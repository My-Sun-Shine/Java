package Learn.D;

import java.lang.annotation.*;

/**
 * @Classname Learn06
 * @Date 2020/3/4 21:19
 * @Created by Falling Stars
 * @Description 自定义注解
 */


/**
 * 这个注解主要的作用是：指定被标注的注解只能出现的位置
 * 指定Learn06注解只能出现在：方法上，构造方法上，类上，接口上，枚举类型上，注解类型上
 */
@Target(value = {ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})

/**
 * 这个注解主要的作用是：指定被标注的注解的保存位置
 *
 * RetentionPolicy.SOURCE	表示注解被保存到java源文件
 * RetentionPolicy.CLASS	表示注解被保存到class文件，但不会被反射机制读取到
 * RetentionPolicy.RUNTIME  表示注解被保存到class文件，并且可以被反射机制读取到
 *
 * 指定Learn06注解将来可以被反射机制读取到
 */
@Retention(value = RetentionPolicy.RUNTIME)

/**
 * 指定Learn06注解可以生成到帮助文档当中
 */
@Documented

/**
 * 指定Learn06注解支持继承，A类注解Learn06，B继承A，同时继承A的注解Learn06
 */
@Inherited
public @interface Learn06 {
    /**
     * 注解当中的属性类型只能是：
     * byte,short,int,long,float,double,boolean,char
     * Byte,Short,Integer,Long,Float,Double,Boolean,Character
     * String、枚举类型以及以上类型的所有数组形式
     */
    String[] values();

    /**
     * 在自定义注解的时候，需要使用其它的注解标注这个注解
     * 专门负责标注注解的注解被称为元注解(meta-annotation)，
     * 元注解有：java.lang.annotation包下：
     * @Target
     * @Retention
     * @Documented
     * @Inherited
     */


}
