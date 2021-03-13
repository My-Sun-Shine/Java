package RedisLearn.Learn;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @Classname RedisStringPool
 * @Date 2021/3/13 22:48
 * @Created by FallingStars
 * @Description
 */
public class RedisStringPool {
    public static void main(String[] args) {
        //创建Jedis对象，调用他的方法，操作redis数据库
        String host = "127.0.0.1";// redis所在的linux的ip
        int port = 6379;// redis运行的端口号
        Jedis jedis = null;
        JedisPool pool = null;
        try {
            //创建Pool
            pool = RedisTools.open(host, port);

            //从Pool中获取Jedis对象
            jedis = pool.getResource();

            //调用Jedis的方法
            //调用Jedis对象的方法
            jedis.set("break", "豆浆和油条111");

            //获取key的值
            String value = jedis.get("break");
            System.out.println("break的值：" + value);

            //一次设置多个key-value
            jedis.mset("lunch", "红烧肉盖饭111", "dinner", "小面111");

            //获取多个key的值
            List<String> values = jedis.mget("break", "lunch", "dinner");
            for (String val : values) {
                System.out.println(val);
            }
        } finally {
            //关闭Jedis对象，把使用完毕的Jedis放回到JedisPool中，让其他的客户端使用
            if (jedis != null) {
                jedis.close();
                RedisTools.close();
            }
        }
    }
}
