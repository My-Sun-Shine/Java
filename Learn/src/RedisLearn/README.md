# Remote Dictionary Server(Redis)
* 一个开源的使用ANSI C语言编写、支持网络、可基于内存亦可持久化的Key-Value数据库
* Key为字符类型，其值(Value)可以是字符串(String), 哈希(Map), 列表(list), 集合(sets)和有序集合(sorted sets)等类型，每种数据类型有自己的专属命令
* 官方网站：https://redis.io
* 官方文档：https://redis.io/documentation
* 中文官网：http://www.redis.cn
* GitHub托管代码：https://github.com/antirez/redis
* 菜鸟教程：http://www.runoob.com/redis/redis-tutorial.html

## Window上安装Redis
* Window安装包下载地址：https://github.com/microsoftarchive/redis/releases
* 下载.msi文件，可直接点击安装，安装完成之后，打开【服务】页面可以进行启动关闭或者命令行启动关闭
* 下载.zip文件，在某目录中解压后，即可使用，直接双击redis-server.exe启动或者在解压目录使用命令行启动
```
在cmd中，cd D:\tools\Redis-x64-3.2.100
# redis.windows.conf 为配置文件
D:\tools\Redis-x64-3.2.100> redis-server.exe redis.windows.conf
```

## Redis 客户端
* Redis命令行客户端
```
# 127.0.0.1是IP地址、6379是redis服务端默认端口，打开redis操作模式
redis-cli.exe -h 127.0.0.1 -p 6379
```
* Redis远程客户端
    * Redis Desktop Manager：https://redisdesktop.com
    * Redis Desktop Manager GitHub：https://github.com/uglide/RedisDesktopManager
    * 远程连接redis需要修改redis主目录下的redis.conf配置文件
```
# Redis服务器有安全保护措施，默认只有本机（安装Redis的那台机器）能够访问
bind ip 绑定ip此行注释
protected-mode yes 保护模式改为 no
```
* RedisClient-win32.x86_64.2.0：直接双击jar使用或者在cmd中执行java -jar redisclient-win32.x86_64.2.0.jar
 
## 密码设置
* 修改redis.conf，找到requirepass行去掉注释，requirepass空格后就是密码
```
requirepass 123456 
```
* 重新启动服务
```
redis-cli.exe -h 127.0.0.1 -p 6379
# 进入redis交互模式之后，由于设置了密码，需要执行auth命令，不然无法执行任何命令
127.0.0.1:6379> auth 123456
```
