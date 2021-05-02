package com.springboot.security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Classname MySecurityConfig
 * @Date 2021/5/2 14:25
 * @Created by FallingStars
 * @Description
 */
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 控制请求的访问权限
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //定制请求的授权规则
        http.authorizeRequests()
                // 允许所有的访问
                .antMatchers("/").permitAll()
                // 对某些地址加访问权限
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");

        // 开启自动配置的登陆功能，效果，如果没有登陆，没有权限就会来到登陆页面
        // 自定义登陆页面，登陆页面表单参数的name与下面保持一致
        http.formLogin().usernameParameter("user").passwordParameter("pwd")
                // 自定义设置登陆请求的链接
                .loginPage("/userlogin");

        //1、/login get请求来到登陆页
        //2、重定向到/login?error表示登陆失败
        //3、更多详细规定
        //4、默认post形式的 /login代表处理登陆请求
        //5、一但定制loginPage；那么 loginPage的post请求就是登陆


        //开启自动配置的注销功能。
        http.logout().logoutSuccessUrl("/");//注销成功以后来到首页
        //1、访问 /logout 表示用户注销，清空session
        //2、注销成功会返回 /login?logout 页面；

        //开启记住我功能
        http.rememberMe().rememberMeParameter("remember");
        //登陆成功以后，将cookie发给浏览器保存，以后访问页面带上这个cookie，只要通过检查就可以免登录
        //点击注销会删除cookie

    }

    /**
     * 定义认证规则
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        auth.inMemoryAuthentication()
                // 在SpringBoot2.x中使用BCrypt加密方式
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("zhangsan")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles("VIP1", "VIP2")
                .and()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("lisi")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles("VIP2", "VIP3")
                .and()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("wangwu")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles("VIP1", "VIP3");

    }
}
