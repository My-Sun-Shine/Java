package C;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

/**
 * @Classname Listener02
 * @Date 2020/3/9 23:30
 * @Created by Falling Stars
 * @Description 监听器接口：监听全局作用域对象的add，update，remove
 */
public class Listener02 implements ServletContextAttributeListener {

    //全局作用域对象添加数据
    @Override
    public void attributeAdded(ServletContextAttributeEvent scae) {
        System.out.println("全局作用域对象添加数据");
    }

    //全局作用域对象删除数据
    @Override
    public void attributeRemoved(ServletContextAttributeEvent scae) {
        System.out.println("全局作用域对象删除数据");
    }

    //全局作用域对象修改数据
    @Override
    public void attributeReplaced(ServletContextAttributeEvent scae) {
        System.out.println("全局作用域对象修改数据");
    }
}
