package com.crm.web.listener;

import com.crm.settings.domain.DicValue;
import com.crm.settings.service.DicService;
import com.crm.settings.service.impl.DicServiceImpl;
import com.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Classname SysInitListener
 * @Date 2020/3/25 22:36
 * @Created by Falling Stars
 * @Description 监听器
 */

/**
 * 监听器：
 * 主要用来监听域对象的创建和销毁，或者是域对象中值得添加，更新与移除
 * <p>
 * 对于所有的监听方法，都会有一个event参数
 * 由该参数可以取得监听的信息
 * <p>
 * 监听的是什么
 * 从event中就能get到什么
 */
public class SysInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("上下文对象已经创建");
        System.out.println("数据字典值初始化到服务缓存开始");
        /*
        将数据保存在上下文域对象中，随取随用，省却掉了每一次都连接数据库去取得数据的麻烦
        以服务器缓存的形式来保存数据的操作（cache）
        一般的表应该都是时常更新的，更新过后，从缓存中取数据取得的就一定是更新前的老数据
        服务器缓存适用在数据更新的不是很频繁的表
        */

        ServletContext application = sce.getServletContext();
        DicService service = (DicService) ServiceFactory.getService(new DicServiceImpl());
        Map<String, List<DicValue>> map = service.getDicInit();

        Set<String> set = map.keySet();
        for (String key : set) {
            List<DicValue> dicValueList = map.get(key);
            application.setAttribute(key, dicValueList);
        }
        System.out.println(map.toString());
        System.out.println("数据字典值初始化到服务缓存结束");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("上下文对象已经销毁");
    }
}
