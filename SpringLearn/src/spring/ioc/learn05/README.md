## 为应用指定多个Spring配置文件

* 总的配置文件，一般不定义对象的，只是用来包含其他的配置文件，使用<import>导入
* 注意：其他配置的位置一般是放在类路径中，Spring中使用"classpath:"关键字表示类路径
* 包含关系中可以使用通配符 * ,表示任意个字符
* 注意：总的文件名，不能包含在通配符的范围内，不能叫spring-total.xml
```
<import resource="其他配置文件的路径和名称" />

<!--
<import resource="classpath:spring/ioc/learn05/spring-student.xml"/>
<import resource="classpath:spring/ioc/learn05/spring-school.xml"/>
-->

<!--
<import resource="spring-school.xml"/>
<import resource="spring-student.xml"/>
-->

<import resource="classpath:spring/ioc/learn05/spring-*.xml"/>
```

    
                 
                 
        
                    
                    
                    
                            
                            
                            
                            
                            
                            
                            
                       