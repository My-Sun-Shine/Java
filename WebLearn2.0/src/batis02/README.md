# MyBatis的动态代理机制
由MyBatis框架为我们提供（不需要自己手写）
它所指的是建立dao层的实现类本身，就是一种代码负担，所以以后由MyBatis的动态代理机制实现，再也不用去写dao层的实现类了
以后dao层就写接口和mapper映射文件就可以

## 开发规则：
1. 在Mapper.xml中将namespace设置为StudentDao.java接口的全限定名
2. 将Mapper.xml中statement的id和StudentDao.java接口的方法名保持一致。
3. 将Mapper.xml中statement的parameterType和StudentDao.java接口的方法输入参数类型保持一致
4. 将Mapper.xml中statement的resultType和StudentDao.java接口的方法输出结果类型保持一致
5. 注意结合业务层动态代理提交事务

### service层的动态代理
代理的是事务机制，需要自己手写，制定了一个工厂ServiceFactory，从工厂中取业务对象，service层习惯于建工厂

### dao层的动态代理

代理的是dao层实现本身，不需要自己手写，现在直接使用取得代理类对象的方法直接取，dao层不需要建立工厂


