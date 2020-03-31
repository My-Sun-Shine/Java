package spring.ioc.learn01;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @Classname Test01
 * @Date 2020/3/30 22:20
 * @Created by Falling Stars
 * @Description 测试类
 */
public class Test01 {
    //创建spring的容器对象， 获取创建好的java对象
    public static void main(String[] args) {
        //1.定义变量保存配置文件的位置
        String configLocation = "spring/ioc/learn01/applicationContext.xml";

        //2.创建spring的容器对象， 根据配置文件的位置使用ApplicationContext接口的实现类
        //如果配置文件是在类路径中（classpath）,需要使用ClassPathXmlApplicationContext
        //ApplicationContext代表applicationContext.xml中所有定义的对象
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);

        //3.从spring中获取对象，使用getBean("<bean>的id")
        SomeService service = (SomeService) context.getBean("SomeService");

        //4.调用对象的业务方法
        service.doSome();
    }


    /**
     * 把spring的配置文件放在项目的根目录下， 和src ，libs是同级的目录
     * 使用实现类是FileSystemXmlApplicationContext
     */
    @Test
    public void test01() {
        String configLocation = "src/spring/ioc/learn01/applicationContext.xml";
        ApplicationContext context = new FileSystemXmlApplicationContext(configLocation);
        SomeService service = (SomeService) context.getBean("SomeService");
        service.doSome();
    }

    /**
     * 对象在什么时候创建？
     * 在创建容器对象时，会创建所有的java对象
     * <p>
     * 优点：加载对象快
     * 缺点：占内存
     */
    @Test
    public void test02() {
        String configLocation = "spring/ioc/learn01/applicationContext.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);//此时对象已经创建好
        SomeService service = (SomeService) context.getBean("SomeService");
        service.doSome();
    }


    @Test
    public void test03() {
        String configLocation = "spring/ioc/learn01/applicationContext.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        //获取容器中对象的个数
        int counts = context.getBeanDefinitionCount();
        System.out.println("容器中定义对象个数：" + counts);

        //输出对象的名称
        String names[] = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
