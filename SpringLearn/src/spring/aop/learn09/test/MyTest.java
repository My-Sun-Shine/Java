package spring.aop.learn09.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.aop.learn09.service.NumberService;

/**
 * @Classname MyTest
 * @Date 2020/4/5 21:46
 * @Created by Falling Stars
 * @Description
 */
public class MyTest {
    public static void main(String[] args) {
        String configLocation = "spring/aop/learn09/applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocation);
        //从spring容器中获取目标对象（代理对象）
        NumberService service = (NumberService) ctx.getBean("NumberService");
        Integer n1 = 20;
        Integer n2 = 30;
        Integer n3 = -100;
        Integer result = service.addNumber(n1, n2, n3);
        System.out.println("调用目标方法的结果：" + result);

    }

}


