# 主从复制
* Redis提供了复制（replication）功能来自动实现多台redis服务器的数据同步，通过部署多台redis，并在配置文件中指定这几台redis之间的主从关系
* 主负责写入数据，同时把写入的数据实时同步到从机器，这种模式叫做主从复制，即master/slave，并且redis默认master用于写，slave用于读，向slave写数据会导致错误
### Redis主从复制实现（master/salve）
### Windows修改配置文件的方式，实现主从复制
0. 主节点6380 从节点6382、6384
1. 新建配置文件redis.windows6380.conf、redis.windows6382.conf、redis.windows6384.conf
2. 修改新建的三个配置文件中的端口号port，并且在redis.windows6382.conf、redis.windows6384.conf中添加slaveof 127.0.0.1 6380绑定主节点
3. 启动三个cmd窗口，cd到redis安装目录，并分布执行下面三个命令，启动服务
```
redis-server.exe redis.windows6380.conf
redis-server.exe redis.windows6382.conf
redis-server.exe redis.windows6384.conf
```
4. 然后重新启动三个cmd窗口，cd到redis安装目录，并分布执行下面三个命令，进入redis交互模式
```
redis-cli.exe -h 127.0.0.1 -p 6380
redis-cli.exe -h 127.0.0.1 -p 6382
redis-cli.exe -h 127.0.0.1 -p 6384
```
5. 在6380主节点窗口执行set命令，可以在从节点6382、6384中执行get命令查到对应数据，不能在从节点中执行set系列命令
6. 查看当前服务信息
```
info replication
```
7. 容灾处理：当Master服务出现故障，需手动将slave中的一个提升为master，剩下的slave挂至新的master上
```
127.0.0.1:6382> slaveof no one          # 将一台slave服务器提升为Master（提升某slave为master）
127.0.0.1:6384> slaveof 127.0.0.1 6382  # 将slave挂至新的master上
```

#### 总结
1. 一个master可以有多个slave
2. slave下线，读请求的处理性能下降
3. master下线，写请求无法执行
4. 当master发生故障，需手动将其中一台slave使用slaveof no one 命令提升为master，其它slave执行slaveof命令指向这个新的master，从新的master处同步数据
5. 主从复制模式的故障转移需要手动操作，要实现自动化处理，这就需要Sentinel哨兵，实现故障自动转移

### 高可用Sentinel哨兵
* Sentinel哨兵是redis官方提供的高可用方案，可以用它来监控多个Redis服务实例的运行情况
* Redis Sentinel是一个运行在特殊模式下的Redis服务器，是在多个Sentinel进程环境下互相协作工作的
* Sentinel系统有三个主要任务：
  * 监控：Sentinel不断的检查主服务和从服务器是否按照预期正常工作
  * 提醒：被监控的Redis出现问题时，Sentinel会通知管理员或其他应用程序
  * 自动故障转移：监控的Redis主节点不能正常工作，Sentinel会开始进行故障迁移操作，将一个从节点升级新的主节点，让其他从节点挂到新的主节点，同时向客户端提供新的主服务器地址
* Sentinel配置文件sentinel.conf
```
port 26379    # 当前Sentinel服务运行的端口

# Sentinel去监视一个名为master的主redis实例，这个主实例的IP地址为本机地址127.0.0.1，端口号为6380，
# 而将这个主实例判断为失效至少需要2个Sentinel进程的同意，只要同意Sentinel的数量不达标，自动failover就不会执行
sentinel monitor master 127.0.0.1 6380 2

# 指定了Sentinel认为Redis实例已经失效所需的毫秒数
# 当实例超过该时间没有返回PING，或者直接返回错误，那么Sentinel将这个实例标记为主观下线
# 只有一个Sentinel进程将实例标记为主观下线并不一定会引起实例的自动故障迁移：只有在足够数量的Sentinel都将一个实例标记为主观下线之后，实例才会被标记为客观下线，这时自动故障迁移才会执行
sentinel down-after-milliseconds master 5000

sentinel config-epoch master 1
sentinel leader-epoch master 1
 
# 指定了在执行故障转移时，最多可以有多少个从Redis实例在同步新的主实例，在从Redis实例较多的情况下这个数字越小，同步的时间越长，完成故障转移所需的时间就越长
sentinel parallel-syncs master 1 

# 如果在该时间（ms）内未能完成failover操作，则认为该failover失败
sentinel failover-timeout master 15000
```
1. 新建sentinel26379.conf、sentinel26479.conf、sentinel26579.conf
2. 启动三个cmd窗口，cd到redis安装目录，执行以下命令
```
redis-server.exe sentinel26379.conf --sentinel
redis-server.exe sentinel26479.conf --sentinel
redis-server.exe sentinel26579.conf --sentinel
```
3. 当主节点6380挂掉后，sentinel会将选出一个从节点6382升级为主节点，并让其它从节点6384连接到该新主节点6382，当旧主节点6380重新链接后，会自动作为一个从节点链接新主节点6382
### 总结
1. Sentinel会不断检查Master和Slave是否正常
2. 如果Sentinel挂掉，就无法监控，所以需要多个哨兵，组成Sentinel网络，一个健康的Sentinel系统至少有3个Sentinel应用，彼此在独立的物理机器或虚拟机
3. 监控同一个Master的Sentinel会自动连接，组成一个分布式的Sentinel网络，互相通信并交换彼此关于被监控服务器的信息
4. 当一个 Sentinel 认为被监控的服务器已经下线时，它会向网络中的其它 Sentinel 进行确认，判断该服务器是否真的已经下线
5. 如果下线的服务器为主服务器，那么 Sentinel 网络将对下线主服务器进行自动故障转移，通过将下线主服务器的某个从服务器提升为新的主服务器，并让其从服务器转移到新的主服务器下，以此来让系统重新回到正常状态
6. 下线的旧主服务器重新上线，Sentinel 会让它成为从，挂到新的主服务器下