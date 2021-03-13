# 命令行
* 命令参考：http://redisdoc.com/index.html
###  Redis基本操作命令
```
# 以下命令都是在Redis操作模式"127.0.0.1:6379 >"中
ping：返回PONG，表示redis服务运行正常
dbsize：返回当前数据库的key的数量
select [index]：使用其它数据库，index为0-15，redis默认使用16个库，可以对conf文件中的"databases 16"配置进行修改
flushdb：删除当前库的数据
exit或quit：退出命令行客户端，redis服务并没有退出
```

### Redis的Ket的操作命令
```
keys [pattern]：查找所有符合pattern的key，pattern可以使用通配符
通配符 *：表示0或者多个字符，例如命令keys *表示查询所有的key
通配符 ?：表示单个字符，例如命令keys wo?d，匹配key为word，wood等的

exists key[key...]：判断一个或者多个key（空格隔开）是否存在
返回值：判断单个key时，存在返回1，其它返回0；判断多个key时，返回存在的key的数量

expire [key] [seconds]：设置key的生存时间（单位是秒），超过时间，key会自动删除
返回值：设置返回数字1，其它情况返回0

ttl [key]：以秒为单位，返回当前key的剩余生存时间
返回值：-1代表该key永不过期；-2代表该key不存在；其它数字表示该key的剩余时间，单位是秒

type [key]：判断该key所存储值得数据类型
返回值：none代表key不存在，string表示字符串，list表示列表，set表示机会，zset表示有序集，hash表示哈希表

del key[key...]：删除存在的key，若key不存在则忽略，返回删除的key的数量
```

### Redis的5种数据类型
* 字符串类型string
* 哈希类型hash：一个string类型的field和value的映射表
* 列表类型list：字符串列表
* 集合类型set：字符串类型的无序集合，集合成员是唯一的
* 有序集合类型zset：字符串类型的有序集合，集合成员是唯一的，zset的每个元素都会关联一个分数，该分数可以重复，用于大小排序

### 字符串类型的操作命令
* 基本命令
```
set [key] [value]：将字符串值value设置到key中，若key已存在，则会覆盖原来的值
get [key]：获取该key设置的字符串值value，若key不存在，则返回nil(表示null)
incr [key]：将该key保存的数字值加1，若key不存在，则被初始化为0后再进行ince操作，得到1；若对非数据类型进行操作，则会报错
decr [key]：将该key保存的数字值减1，若key不存在，则被初始化为0后再进行decr操作，得到-1；若对非数据类型进行操作，则会报错
append [key] [value]：如果key存在，则将value追加到旧值得末尾；如果key不存在，则将key设置值为value，返回追加字符串之后的总长度
```
* 常用命令
```
strlen [key]：返回key所存储的字符串值得长度，若key不存在，则返回0
mset [key value][key value...]：同时设置一个或者多个key-value对，设置成功返回ok
mget key[key...]：获取一个或者多个key（空格隔开）的值，返回值得列表

getrange [key] [start] [end]：获取该key中字符串值从start开始到end结束的子字符串，包括start和end
setrange [key] [start] [end]：设置该key中字符串值从start开始到end结束的子字符串，包括start和end
0代表字符串第一个字符，依次往后，1代表第二个字符
负数表示从字符串的末尾开始，-1代表最后一个字符，-2代表倒数第二个字符
```

### 哈希类型的操作命令
* 基本命令
```
hset [key] [field] [value]：将哈希表key中的域field的值设置为value，如果key不存在，则新建hash表，执行赋值，如果有field，则覆盖值
返回值：如果field是hash表的中心filed，且设置值成功，返回1；如果field已经存在，旧值覆盖新值，返回0
hget [key] [field]：获取哈希表key中给定的field的值，如果key不存在或者field不存在则返回nil
hmset [key] [field value]/[field value...]：同时将多个field-value(域-值)设置到哈希表key中，该命令会覆盖已经存在的field，设置成功返回OK
hmget [key] [field]/[field...]：获取哈希表key中一个或多个给定域field的值，返回和field顺序对应的值
hgetall [key]：获取哈希表key中所有的域和值，以列表的形式返回hash中域和域的值，若key不存在，则返回空hash
hdel [key] [field]/[field...]：删除哈希表key中的一个或多个指定域field，不存在field直接忽略，返回成功删除filed的数量
```
* 常用命令
```
hkeys [key]：查看哈希表key中所有的field域的列表，若key不存在，则返回空列表
hvals [key]：查看哈希表key中所有的域值value的列表，若key不存在，则返回空列表
hexists [key] [field]：判断哈希表key中域field是否存在，若存在返回1 其它返回0
```
### 列表类型的操作命令
* 基本命令
```
lpush [key] [value]/[value...]：将一个或多个value值插入到列表key的表头（最左边），各值从左到右依次插入到表头（最后插入的在最左边），返回列表的长度
rpush [key] [value]/[value...]：将一个或多个value值插入到列表key的表尾（最右边），各值从左到右依次插入到表尾（最后插入的在最右边），返回列表的长度
llen [key]：获取列表key的长度，若key不存在则返回0

lrange [key] [start] [stop]：获取列表key中指定区间内的元素，start、stop是列表的下标值，也可以负数的下标
lindex [key] [index]：获取列表key中下标为指定index的元素，index同样可以为正数，也可以为负数
0表示列表第一个元素，1表示第二个元素；-1表示列表最后一个，-2表示列表倒数第二个元素
```
* 常用命令
```
lrem [key] [count] [value]：根据参数count的值，移除列表中与参数value相等的元素，count为移除元素的数量
count>0时从列表的头部(左侧)开始移除、count<0时从列表的尾部(右侧)开始移除、count=0时移除表中所有与value相等的元素

lset [key] [index] [value]：将列表key下标为index的元素的值设置为value，设置成功返回OK，若key不存在或index超出范围返回错误信息
linsert [key] BEFORE|AFTER [pivot] [value]：将值value插入到列表key当中，位于值pivot之前或之后的位置，若key不存在或者pivot不存在则不进行操作
返回值：命令执行成功，返回新列表的长度；没有找到pivot返回-1，key不存在返回0
```
### 集合类型的操作命令
* 基本命令
```
sadd [key] [member]/[member...]：将一个或多个member元素加到集合key中，已经存在于集合key中的元素将被忽略，不会再加入；返回加入到集合的新元素，不包括被忽略的元素
smembers [key]：查看集合key中的所有成员元素，不存在的key返回空集合
sismember [key] [member]：判断member元素是否是集合key的成员，如果是返回1，其它返回0
scard [key]：获取集合里面的元素个数，若集合存在返回key的元素个数，其它情况返回0
srem [key] [member]/[member...]：删除集合key中的一个或多个member元素，不存在的元素则被忽略；返回成功删除的元素个数，不包括被忽略的元素
```
* 常用命令
```
srandmember [key] [count]：只提供key参数，随机返回集合key中的一个元素，元素不删除，依然存在于集合中
提供count元素时，count为正数时，返回包含count个数元素的集合，集合元素各不相同；count为负数时。返回一个count绝对值的长度的集合，集合中元素可能会重复多次

spop [key] [count]：随机从集合中删除count个元素，不写count时默认删除一个元素；返回被删除的元素，key不存在或空集合时返回null
```
### 有序集合类型的操作命令
* 基本命令
```
zadd [key] [score memeber]/[score memeber...]：将一个或多个member元素及其score值(整数或者浮点数)加入到有序集合key中，如果member存在于集合中 则更新值
zrange [key] [start] [stop] [WITHSCORES]：查询有序集合key，指定区间内的元素，集合成员按照score值从小到大来排序，withscores选项让score和value一同返回
zrevrange [key] [start] [stop] [WITHSCORES]：查询有序集合key，指定区间内的元素，集合成员按照score值从大到小（递减）来排序，withscores选项让score和value一同返回
0表示列表第一个元素，1表示第二个元素；-1表示列表最后一个，-2表示列表倒数第二个元素

zrem [key] [member]/[member...]：删除集合key中的一个或多个member元素，不存在的元素则被忽略；返回成功删除的元素个数，不包括被忽略的元素
zcard [key]：获取集合里面的元素个数，若集合存在返回key的元素个数，其它情况返回0
```
* 常用命令
```
zrangebyscore [key] [min] [max] [WITHSCORES][LIMIT offset count]：获取有序集合key中，所有的score值介于min和max之间（包括min和max）的成员，按照递增从小到大排序
zrevrangebyscore [key] [min] [max] [WITHSCORES][LIMIT offset count]：获取有序集合key中，所有的score值介于min和max之间（包括min和max）的成员，按照递减从大到小排序
zcount [key] [min] [max]：返回有序集合key中，score 值在 min 和 max 之间(默认包括score值等于min或max)的成员的数量

使用(min (max，则变成min< score <max；min，max可以使用-inf，+inf来表示最小和最大，withscores选项让score和value一同返回
limit用来限制返回结果的数量和区间，类似于mysql中的limit offset,count，表示跳过offset个元素，然后获取count个元素
```