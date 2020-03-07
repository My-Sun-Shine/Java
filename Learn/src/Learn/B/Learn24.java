package Learn.B;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @Classname Learn24
 * @Date 2020/2/28 14:57
 * @Created by Falling Stars
 * @Description Properties集合操作，配置文件读取
 */
public class Learn24 {
    public static void main(String[] args) throws IOException {
        /*
        配置文件config.properties的内容
        username = aaa
        password : 123465
         */
        m1();
        m2();
        m3();
    }


    private static void m1() {
        //创建Properties,
        Properties properties = new Properties();

        //设置系统属性
        properties.setProperty("username", "aaa");
        properties.setProperty("password", "666");

        //读取属性值
        System.out.println(properties.getProperty("username"));
        System.out.println(properties.getProperty("password"));
    }

    /**
     * 1) 经常把系统属性保存在配置文件中
     * 2) 一般情况下, 会单独的创建一个资源包, 在该资源包中添加一个配置文件, 配置文件后缀名是.properties
     * 3) 通过Properties读取配置文件中的属性
     *
     * @throws IOException
     */
    private static void m2() throws IOException {
        //1)创建Properties
        Properties properties = new Properties();

        //2) 通过Properties加载配置文件的内容
        //InputStream in = Learn24.class.getResourceAsStream("/Learn/b/config.properties");
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("Learn/b/config.properties");
        properties.load(in);

        //3) 读取属性
        System.out.println(properties.getProperty("username"));
        System.out.println(properties.get("password"));
    }

    /**
     * 经常使用ResourceBundle读取配置文件
     */
    private static void m3() {
        //不需要斜杠开头, 也不需要配置文件的扩展名(前提是配置文件的扩展名properties写正确)
        ResourceBundle bundle = ResourceBundle.getBundle("Learn/b/config");
        System.out.println(bundle.getString("username"));
        System.out.println(bundle.getString("password"));
    }

}
