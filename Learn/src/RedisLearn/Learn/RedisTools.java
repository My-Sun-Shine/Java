package RedisLearn.Learn;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * @Classname RedisTools
 * @Date 2021/3/13 22:48
 * @Created by FallingStars
 * @Description
 */
/**
 * 创建线程池。在池中是多个Jedis对象。
 * 在项目启动时候，创建一个线程池就可以了。
 * 在程序结束时，关闭线程池。
 */
public class RedisTools {

    private static JedisPool pool = null;

    //创建线程池
    public static JedisPool open(String host, int port) {
        if (pool == null) {
            //创建JedisPool

            //JedisPoolConfig配置器，用来设置线程池的参数的
            JedisPoolConfig config = new JedisPoolConfig();
            //设置线程池的最大线程的个数
            config.setMaxTotal(100);
            //设置线程池的最大空闲数
            config.setMaxIdle(3);
            //设置检查项，保证从线程池中获取的Jedis对象不是null
            config.setTestOnBorrow(true);
            /**
             * poolConfig:配置器
             * host：redis的linux服务器的ip
             * port:redis的端口
             * timeout:超时时间，毫秒值。
             * password:redis的密码
             */
            pool = new JedisPool(config, host, port, 6000, "123456");

        }
        return pool;
    }

    //关闭JedisPool
    public static void close() {
        if (pool != null) {
            pool.close();
            pool = null;
        }
    }

}