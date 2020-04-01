## 引用类型的自动注入，Spring框架支持常用的自动注入byName, byType

### byName:按名称注入
* java类中引用类型的[属性名]和spring容器中(xml配置文件)<bean>的[id名称]一样的，且数据类型是一样的，这样的bean能够赋值给引用类型
```
<!--指定byName的语法-->
<bean id="xx" class="yy" autowire="byName">
    ..
</bean>
```

### byType 
* java类中引用类型的【数据类型】和Spring容器(xml配置文件)<bean>的class是同源关系的bean，把这样的bean能够赋值到引用类型
* 同源关系：
    * java类中引用类型的数据类型和<bean>的class是一样的。
    * java类中引用类型的数据类型和<bean>的class是父子类关系的。
    * java类中引用类型的数据类型和<bean>的class是接口和实现类关系的。
* 注意：在xml文件中，使用byType,符合条件的对象只能有一个，多余一个报错的
```
<!--byType的使用语法-->
<bean id="xxx" class="yyy" autowire="byType" >
    <property name="简单类型的属性名" value="属性值" />
</bean>
```
                 
                 
        
                    
                    
                    
                            
                            
                            
                            
                            
                            
                            
                       