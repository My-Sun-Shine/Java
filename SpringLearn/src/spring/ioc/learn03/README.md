## DI(依赖注入,注入就是赋值的意思,就是bean的属性赋)的分类
* 设值注入：Spring调用类的set方法，完成属性赋值， 在set方法中给属性赋值
    * 简单类型的设值注入，简单类型：Spring中把java基本类型和String都叫简单类型
    * 引用类型
```
<!--简单类型的设值注入-->
<bean id="xxx" class="yyy">
    <property name="属性名" value="简单类型的属性值"> // 一个property对应一个属性
    ...
</bean>

<!--引用类型-->
<!--使用ref属性-->
<bean id="xx" class="yy">
    <property name="属性名" ref="bean的id" />
    ...
</bean>
<!--使用ref子标签-->
<bean id="xx" class="yy">
    <property name="属性名" >
        <ref bean="bean的id"/>
    </property>
    ...
</bean>
```
* 构造注入：Spring调用类的有参数构造方法，在构造方法中完成属性的赋值，同时创建对象
    * 标签是<constructor-arg>, 这个标签标示构造方法的参数
    * value:简单类型的参数值
    * ref:引用类型的参数值 
    * 使用name属性: name是构造方法的形参名称
    * 使用index属性：index是构造方法的形参位置，从0开始
    * 省略index属性，默认0 1 2的顺序
        
        
                    
                    
                    
                            
                            
                            
                            
                            
                            
                            
                       