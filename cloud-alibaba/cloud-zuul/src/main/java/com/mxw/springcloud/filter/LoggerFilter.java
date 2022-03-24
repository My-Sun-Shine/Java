package com.mxw.springcloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname LoggerFilter
 * @Date 2021/7/19 10:48
 * @Created by FallingStars
 * @Description Zuul过滤器，必须继承ZuulFilter父类
 */
@Log4j2
@Component
public class LoggerFilter extends ZuulFilter {

    /**
     * 过滤器的类型
     * pre --- 前置过滤
     * route --- 路由后过滤
     * error --- 异常过滤
     * post --- 远程服务调用后过滤
     * 流程：pre --> route --> application service --> post
     * 如果抛异常之后，error --> post
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 同种类的过滤器的执行顺序
     * 按照返回值的自然升序执行
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 返回boolean类型，代表当前filter是否生效
     * 默认为false
     * 返回true代表开启filter
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /***
     * run方法就是过滤器的具体逻辑
     * return返回任意对象
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //通过zuul，获取请求上下文
        RequestContext rc = RequestContext.getCurrentContext();
        HttpServletRequest request = rc.getRequest();
        log.info("LogFilter...method={},url={}",
                request.getMethod(), request.getRequestURL().toString());
        //可以记录日志、鉴权，给维护人员记录提供定位协助、统计性能
        return null;
    }
}
