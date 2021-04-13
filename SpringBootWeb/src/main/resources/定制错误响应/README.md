# SpringBoot默认的错误处理机制

* 针对浏览器，会自动返回一个默认的错误页面；因为浏览器的请求头中包含text/html
* 针对其它客户端，会默认返回一个json数据

### 原理

* **ErrorMvcAutoConfiguration：** 框架默认的错误处理的自动化配置，为容器添加一下组件
* **DefaultErrorAttributes：** 添加错误信息
* **BasicErrorController：** 处理默认的/error请求，针对不同的情况执行不同的方法
* **ErrorPageCustomizer：** 系统出现错误以后来到error请求进行处理
* **DefaultErrorViewResolver：** 响应页面

```java
//一旦系统出现4xx或者5xx之类的错误，ErrorPageCustomizer就会生效，就会来到/error请求，被BasicErrorController处理，具体如何响应页面由DefaultErrorViewResolver处理
public class DefaultErrorAttributes implements ErrorAttributes, HandlerExceptionResolver, Ordered {
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = this.getErrorAttributes(webRequest, options.isIncluded(Include.STACK_TRACE));
        if (Boolean.TRUE.equals(this.includeException)) {
            options = options.including(new Include[]{Include.EXCEPTION});
        }
        if (!options.isIncluded(Include.EXCEPTION)) {
            errorAttributes.remove("exception");
        }
        if (!options.isIncluded(Include.STACK_TRACE)) {
            errorAttributes.remove("trace");
        }
        if (!options.isIncluded(Include.MESSAGE) && errorAttributes.get("message") != null) {
            errorAttributes.put("message", "");
        }
        if (!options.isIncluded(Include.BINDING_ERRORS)) {
            errorAttributes.remove("errors");
        }
        return errorAttributes;
    }
}

@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class BasicErrorController extends AbstractErrorController {
    @RequestMapping(produces = {"text/html"})
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        //获取状态码  
        HttpStatus status = this.getStatus(request);
        //getErrorAttributes获取错误信息
        Map<String, Object> model = Collections.unmodifiableMap(this.getErrorAttributes(request, this.getErrorAttributeOptions(request, MediaType.TEXT_HTML)));
        response.setStatus(status.value());
        //resolveErrorView中获取所有ErrorViewResolve得到ModelAndView
        ModelAndView modelAndView = this.resolveErrorView(request, response, status, model);
        //如何没有找到任何视图，就去找error视图，即在ErrorMvcAutoConfiguration中@Bean(name = {"error"})
        return modelAndView != null ? modelAndView : new ModelAndView("templates/error", model);
    }

    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = this.getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity(status);
        } else {
            Map<String, Object> body = this.getErrorAttributes(request, this.getErrorAttributeOptions(request, MediaType.ALL));
            return new ResponseEntity(body, status);
        }
    }
}

/**
 * 根据ErrorPageCustomizer类中的registerErrorPages方法中获取路径this.properties.getError().getPath()
 * 系统出现错误以后来到error请求进行处理；（web.xml注册的错误页面规则）
 */
public class ErrorProperties {
    @Value("${error.path:/error}")
    private String path = "/templates/error";
}

public class DefaultErrorViewResolver implements ErrorViewResolver, Ordered {
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        ModelAndView modelAndView = this.resolve(String.valueOf(status.value()), model);
        if (modelAndView == null && SERIES_VIEWS.containsKey(status.series())) {
            modelAndView = this.resolve((String) SERIES_VIEWS.get(status.series()), model);
        }
        return modelAndView;
    }

    private ModelAndView resolve(String viewName, Map<String, Object> model) {
        // 默认SpringBoot可以去找到一个页面？error/404
        String errorViewName = "templates/error/" + viewName;
        //模板引擎可以解析这个页面地址就使用模板引擎解析
        TemplateAvailabilityProvider provider = this.templateAvailabilityProviders.getProvider(errorViewName, this.applicationContext);
        //模板引擎可用的情况下返回到errorViewName指定的视图地址，若不可用，就在静态资源文件夹中找errorViewName对应的页面 error/404.html
        return provider != null ? new ModelAndView(errorViewName, model) : this.resolveResource(errorViewName, model);
    }

    private ModelAndView resolveResource(String viewName, Map<String, Object> model) {
        String[] var3 = this.resources.getStaticLocations();
        int var4 = var3.length;
        for (int var5 = 0; var5 < var4; ++var5) {
            String location = var3[var5];
            try {
                Resource resource = this.applicationContext.getResource(location);
                resource = resource.createRelative(viewName + ".html");
                if (resource.exists()) {
                    return new ModelAndView(new DefaultErrorViewResolver.HtmlResourceView(resource), model);
                }
            } catch (Exception var8) {
            }
        }
        return null;
    }
}
```

## 定制错误响应

### 定制响应错误的页面

* **有模板引擎的情况下**：将错误页面命名为 错误状态码.html 放在模板引擎文件夹里面的error文件夹下，发生此状态码的错误就会来到对应的页面
    * 可以使用4xx和5xx作为错误页面的文件名来匹配这种类型的所有错误，精确优先（优先寻找精确的状态 码.html）
* **没有模板引擎**（模板引擎找不到这个错误页面），静态资源文件夹下找error/xxx.html
* 以上都没有错误页面，就是默认来到SpringBoot默认的错误提示页面error视图
* 页面能获取的信息DefaultErrorAttributes：
    * timestamp：时间戳
    * status：状态码
    * error：错误提示
    * exception：异常对象
    * message：异常消息

### 定制响应错误的JSON数据

* 自定义异常处理&返回定制json数据；

```java
//没有自适应效果，无论是浏览器访问还是其它客户端访问都是返回json数据
@ControllerAdvice
public class MyExceptionHandler {
    @ResponseBody
    @ExceptionHandler(UserNotExistException.class)
    public Map<String, Object> handleException(Exception e) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "user.notExist");
        map.put("message", e.getMessage());
        return map;
    }
}
```

```java
//转发到/error进行自适应响应效果处理
@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(UserNotExistException.class)
    public String handleException(Exception e, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //传入我们自己的错误状态码  4xx 5xx，否则就不会进入定制错误页面的解析流程
        // Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        request.setAttribute("javax.servlet.error.status_code", 500);
        map.put("code", "user.notExist");
        map.put("message", e.getMessage());
        //将额外数据放到request域中
        request.setAttribute("ext", map);
        //转发到/error
        return "forward:/error";
    }
}
```

* 将自定义的定制错误数据携带出去
    * 系统出现错误以后，会来到/error请求，会被BasicErrorController处理，响应出去可以获取的数据是由getErrorAttributes()
      得到的（是AbstractErrorController（ErrorController）规定的方法）
    * 完全来编写一个ErrorController的实现类【或者是编写AbstractErrorController的子类】，放在容器中
    * 页面上能用的数据，或者是json返回能用的数据都是通过errorAttributes.getErrorAttributes()得到

```java
//给容器中加入自定义的ErrorAttributes
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, options);
        map.put("company", "奥特之星");
        //异常处理器携带的数据
        Map<String, Object> ext = (Map<String, Object>) webRequest.getAttribute("ext", 0);
        map.put("ext", ext);
        return map;
    }
}
```

```json
{
  "timestamp": "2021-04-10T15:26:21.444+00:00",
  "status": 455,
  "error": "Http Status 455",
  "exception": "com.springbootweb.exception.UserNotExistException",
  "message": "用户不存在",
  "path": "/hello/m1",
  "company": "奥特之星",
  "ext": {
    "code": "user.notExist",
    "message": "用户不存在"
  }
}
```