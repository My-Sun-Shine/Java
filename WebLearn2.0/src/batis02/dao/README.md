# MyBatis的映射文件
### parameterType：设置参数类型
1. parameterType用于设置输入参数的java类型，值为参数类型的java类型或者别名
2. 直接可以使用基本数据类型以及常用的引用类型作为参数
3. Sql语句获取参数的值使用#{}或者${}
4. 该属性在使用时可省略
5. 使用map为参数，#{}中的标识符必须是map中的key（通过key取真正的value参数值）
### #{}与${}
1. '#{}：表示占位符，可以有效防止sql注入，使用#{}设置参数无需考虑参数的类型
2. ${}：表示拼接符，无法防止sql注入，使用${}设置参数必须考虑参数的类型
3. 传递简单类型参数
    1. 如果获取简单类型参数，#{}中可以使用value或其它名称
    2. 如果获取简单类型参数，${}中只能使用value
	3. 注意引号的引入：select * from tbl_student where id='${value}'
4. 在没有特殊要求的情况下，通常使用#{}占位符
5. 有些情况必须使用${}
    1. 需要动态拼接表名：select * from ${tablename}
    2. 动态拼接排序字段：select * from tablename order by ${username} desc
6. 使用${}和#{} 执行like模糊查询
```
String sql = “select * from”;
sql+=” tbl_student”
select * from ? X
因为?仅仅只能代替查询条件中参数的取值
比如 where id=?
```
### resultType：设置返回值类型
1. 返回多条记录，resultType设置为集合的泛型
2. 返回基本数据类型和以及常用的引用类型
3. 返回pojo(domain(Student))
4. 返回hashmap类型(注意返回值类型),比如group by语句
5. 当查询字段名和pojo属性名不一致时的解决方案
    1. 为字段起别名，别名为类中属性名(tbl_student1为老表，接收类为Student，fullname和name发生不一致情况)
    2. 使用resultMap
```
    <!--
    起别名，将老表中的fullname重新命名为新类中的name字段
    为字段取得别名就是类中的属性名，就可以接收到值
    -->
<select id="select12" resultType="Student">
    SELECT id,fullname AS name,age FROM tbl_student1
</select>
```
```
<resultMap id="stuMap" type="Student">
    <!--
    column:表字段
    property:类属性
    -->
    <!-- id是用来对主键进行匹配的 -->
    <id column="id" property="id"/>
    <!-- result是用来对普通字段进行匹配的 -->
    <result column="fullname" property="name"/>
    <result column="age" property="age"/>
</resultMap>
<select id="select13" resultMap="stuMap">
    SELECT * FROM tbl_student1
</select>
```



