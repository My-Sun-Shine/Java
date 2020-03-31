## Bean的装配
* Bean 的装配，即 Bean 对象的创建。容器根据代码要求创建 Bean 对象后再传递给代码的过程，称为 Bean 的装配
* 默认装配方式：Spring调用Bean类的无参构造函数，创建空值的实例对象

## Bean的作用域，使用Bean元素的scope属性
* singleton：单例模式，该Bean是在容器被创建时即被装配好了
* prototype：原型模式，Bean实例是在代码中使用该Bean实例时才进行装配的
* request：对于每次 HTTP 请求，都将会产生一个不同的 Bean 实例
* session：对于每个不同的 HTTP session，都将产生一个不同的 Bean 实例