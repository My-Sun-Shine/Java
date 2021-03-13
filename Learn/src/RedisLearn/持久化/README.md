# 持久化方式

### RDB方式 Redis Database

#### 定义
* 就是在指定的时间间隔内将内存中的数据集快照写入磁盘，数据恢复时将快照文件直接再读到内存
* RDB保存了在某个时间点的数据集（全部数据），存储在一个二进制文件中，只有一个文件；默认是 dump.rdb
* RDB技术非常适合做备份，可以保存最近一个小时，一天，一个月的全部数据
* 保存数据是在单独的进程中写文件，不影响Redis的正常使用
* RDB恢复数据时比其他AOF速度快

#### 实现
* RDB方式的数据持久化，仅需在redis.conf 文件中配置即可，默认配置是启用的
* 在配置文件redis.conf中搜索 SNAPSHOTTING
    * save：配置执行RDB生成快照文件的时间策略，对Redis进行设置，让它在"N秒内数据集至少有M个key改动"这一条件被满足时，自动保存一次数据集
    * dbfilename：设置 RDB 的文件名，默认文件名为 dump.rdb
    * dir：指定 RDB 文件的存储位置，默认是 ./ 当前目录
```
## 配置格式：save <seconds> <changes>
save 900 1    # 900秒内数据集至少有1个key改动
save 300 10
save 60 10000
```

#### 总结
* 优点：由于存储的是数据快照文件，恢复数据很方便，也比较快
* 缺点：
    * 会丢失最后一次快照以后更改的数据
    * 由于需要经常操作磁盘，RDB 会分出一个子进程，如果redis数据库很大的话，子进程占用比较多的时间，并且可能会影响Redis暂停服务一段时间（millisecond 级别）

### AOF方式 Append-only File

#### 定义
* Redis每次接收到一条改变数据的命令时，它将把该命令写到一个AOF文件中（只记录写操作，读操作不记录）
* 当Redis重启时，它通过执行AOF文件中所有的命令来恢复数据

#### 实现
* AOF方式的数据持久化，仅需在 redis.conf 文件中配置即可
```
### 配置项
# 默认是no，改成yes，即开启了AOF持久化
appendonly no

# 指定AOF文件名，默认文件名为appendonly.aof
appendfilename "appendonly.aof"

# 指定RDB和AOF文件存放的目录，默认是 ./   
dir "./"    

# 配置向AOF文件写命令数据的策略：
appendfsync [no/always/everysec]
    # no：不主动进行同步操作，而是完全交由操作系统来做（即每30秒一次），比较快但不是很安全
    # always：每次执行写入都会执行同步，慢一些但是比较安全
    # everysec：每秒执行一次同步操作，比较平衡，介于速度和安全之间，这是默认项

# 允许可以重写（压缩文件）AOF文件时的大小，默认是64M，当AOF文件大于64M时，开始整理AOF文件，去掉无用的操作命令，缩小AOF文件
auto-aof-rewrite-min-size 64M
```

#### 总结
* AOF文件会在操作过程中变得越来越大，但Redis支持在不影响服务的前提下在后台重构AOF文件，让文件得以整理变小
* 可以同时使用RDB和AOF两种方式，Redis默认优先加载AOF文件（AOF数据最完整）