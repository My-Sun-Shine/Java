package spring.ioc.learn07;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname SpringConfig
 * @Date 2020/4/1 23:19
 * @Created by Falling Stars
 * @Description 注解@Configuration就相当于xml配置文件
 */
@Configuration
public class SpringConfig {

    //方法名称默认为bean的id
    @Bean
    public SomeService mySomeService() {
        SomeService someService = new SomeServiceImpl();
        return someService;
    }

    //使用name或者value自定义bean的id
    @Bean(value = "SomeService01")
    //@Bean(name = "SomeService01")
    public SomeService mySomeService01() {
        SomeService someService = new SomeServiceImpl();
        return someService;
    }
}
