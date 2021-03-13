# Jedis操作Redis
* Jedis完全兼容redis 2.8.x and 3.x.x
* Jedis 源码：https://github.com/xetorthio/jedis
* api 文档：http://xetorthio.github.io/jedis/
* Jedis对象并不是线程安全的，在多线程下使用同一个Jedis对象会出现并发问题，为了避免每次使用Jedis对象时都需要重新构建，Jedis提供了JedisPool
* JedisPool是基于Commons Pool 2 实现的一个线程安全的连接池
* 下载Jedis和Commons-Pool
    * 下载地址：http://search.maven.org/ ，搜索 jedis、commons-pool2