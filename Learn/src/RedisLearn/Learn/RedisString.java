package RedisLearn.Learn;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @Classname RedisString
 * @Date 2021/3/13 22:37
 * @Created by FallingStars
 * @Description
 */
public class RedisString {
    public static void main(String[] args) {
        //创建Jedis对象，调用他的方法，操作redis数据库
        String host = "127.0.0.1";// redis所在的linux的ip
        int port = 6379;// redis运行的端口号
        Jedis jedis = new Jedis(host, port);

        //设置访问密码
        jedis.auth("123456");

        //调用Jedis对象的方法
        jedis.set("break", "豆浆和油条");

        //获取key的值
        String value = jedis.get("break");
        System.out.println("break的值："+value);

        //一次设置多个key-value
        jedis.mset("lunch","红烧肉盖饭","dinner","小面");

        //获取多个key的值
        List<String> values  = jedis.mget("break","lunch","dinner");
        for(String val:values){
            System.out.println(val);
        }
    }
}
