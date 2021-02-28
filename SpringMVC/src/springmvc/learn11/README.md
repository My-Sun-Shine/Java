### SpringMVC 拦截器
* 主要的目的是拦截用户的请求，对请求做判断，根据判断的结果，可以执行请求的处理，也可以截断请求
* 拦截器的执行时间点：在获取到处理器的适配器对象之后，在处理器方法执行之前拦截用户的请求
* 拦截器的特点：
  * 拦截器是拦截动态资源的请求（能够被中央调度器获取的请求）
  * 拦截器是全局的，对所有的处理器都可以使用拦截器
* 把处理器类中异常处理代码提取处理放到单独的类
* 拦截器的实现步骤：
  * 定义类实现HandlerInterceptor接口
  * 在SpringMVC配置文件中声明拦截器对象
```
/**
     * preHandle:预处理方法， 在请求之前先执行的方法。 可以对请求做预先的判断和处理。
     * 参数：
     * Object handler：处理器对象
     * <p>
     * 返回值：boolean
     * true: 拦截可以让请求通过，说明请求是正确的，可以执行afterCompletion()
     * false：拦截器认为请求是不正确的，不能被处理器处理，拦截器的afterCompletion()不会执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
             throws Exception {
    }

    /**
     * postHandle：后处理方法，在处理器方法执行之后执行的。能够获取到处理器方法的执行结果（ModelAndView）
     * 可以对执行做修改，修改数据，修改视图。 是对原来的执行结果的二次修正。
     * 参数：
     * Object handler：处理器对象。
     * ModelAndView mv：处理器方法的执行结果
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv)
            throws Exception {
    }

    /**
     * afterCompletion：最后执行的方法，在视图对象（View）处理完成后执行的
     * afterCompletion执行意味请求处理完成，可以把请求过程中占用的资源在这里释放，它是程序最后执行的方法
     * 参数：
     * Object handler：处理器对象
     * Exception ex:异常对象
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) 
        throws Exception {
    }
```