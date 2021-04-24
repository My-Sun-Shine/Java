package com.springboot.cache.service;

import com.springboot.cache.bean.Employee;
import com.springboot.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @Classname EmployeeService
 * @Date 2021/4/24 16:02
 * @Created by FallingStars
 * @Description
 */
@Service
// 抽取缓存的公共配置
@CacheConfig(cacheNames = "employee")
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 注解@Cacheable：标注的方法执行之前先来检查缓存中有没有这个数据，默认按照参数的值作为key去查询缓存，
     * 如果没有就运行方法并将结果放入缓存，以后再来调用就可以直接使用缓存中的数据
     * <p>
     * 注解属性：
     * cacheNames/value：指定缓存组件的名字，将方法的返回结果放在哪个缓存中，是数组的格式，可以指定多个缓存
     * <p>
     * key：缓存数据使用的key；可以用它来指定。默认是使用方法参数的值；可以编写SpEL； #id、#a0、#p0、#root.args[0]都代表参数id的值
     * keyGenerator：key的生成器；可以自己指定key的生成器的组件id
     * key/keyGenerator：二选一使用
     * <p>
     * cacheManager：指定缓存管理器
     * cacheResolver：指定获取解析器
     * <p>
     * condition：指定符合条件的情况下才缓存，例如condition = "#id>0"，id参数>0的时候才进行缓存
     * unless:否定缓存；当unless指定的条件为true，方法的返回值就不会被缓存
     * unless="#result==null"：可以对获取到结果进行判断
     * unless="#a0==2"：如果第一个参数的值是2，结果不缓存
     * <p>
     * sync：是否使用异步模式，默认false
     * <p>
     * <p>
     * 运行流程：以SimpleCacheConfiguration为例
     * <p>
     * 1、SimpleCacheConfiguration为容器注册CacheManager组件ConcurrentMapCacheManager
     * <p>
     * 2、方法运行之前
     * ConcurrentMapCacheManager使用getCache()先获取相应的缓存，第一次获取缓存如果没有Cache组件会自动创建
     * 然后再去查询Cache（缓存组件ConcurrentMapCache），使用lookup()按照cacheNames指定的名字获取
     * <p>
     * 3、去Cache中查找缓存的内容，使用一个key，默认就是方法的参数，generateKey()
     * key是按照某种策略生成的；默认是使用keyGenerator生成的，默认使用SimpleKeyGenerator生成key
     * SimpleKeyGenerator生成key的默认策略：
     * 如果没有参数；key=new SimpleKey()
     * 如果有一个参数：key=参数的值
     * 如果有多个参数：key=new SimpleKey(params)
     * <p>
     * 7、没有查到缓存就调用目标方法；
     * 5、将目标方法返回的结果，放进缓存中
     */
    @Cacheable(value = {"employee"}, key = "#id")
    //@Cacheable(value = {"employee"}, keyGenerator = "myKeyGenerator")//指定自定义KeyGenerator
    public Employee getEmp(Integer id) {
        System.out.println("查询" + id + "号员工");
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
    }

    /**
     * 注解@CachePut：既调用方法，修改了数据库的某个数据，同时更新缓存
     * 运行时机：1、先调用目标方法   2、将目标方法的结果缓存起来
     * 注解@Cacheable的key是不能用#result：@Cacheable是在方法运行之前执行，result为null
     * 测试步骤：
     * 1、查询1号员工；查到的结果会放在缓存中
     * 2、再次查询还是之前的结果
     * 3、更新1号员工，将方法的返回值也放进缓存了
     * 4、查询1号员工，是更新后的员工
     * <p>
     * key = "#employee.id":使用传入的参数的员工id；
     * key = "#result.id"：使用返回后的id
     * 不指定key的时候，key是传入的employee对象，值是返回的employee对象；
     */
    @CachePut(value = {"employee"}, key = "#result.id")
    public Employee updateEmp(Employee employee) {
        System.out.println("updateEmp:" + employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }

    /**
     * 注解@CacheEvict：缓存清除
     * <p>
     * key：指定要清除的数据
     * allEntries = true：指定清除这个缓存中所有的数据
     * <p>
     * 缓存的清除是否在方法之前执行
     * beforeInvocation = false：默认代表缓存清除操作是在方法执行之后执行，如果出现异常缓存就不会清除
     * beforeInvocation = true：代表清除缓存操作是在方法运行之前执行，无论方法是否出现异常，缓存都清除
     */
    @CacheEvict(value = {"employee"}, key = "#id")
    public void deleteEmp(Integer id) {
        System.out.println("deleteEmp:" + id);
        employeeMapper.deleteEmpById(id);
        //int i = 10/0;
    }

    /**
     * 定义复杂的缓存规则
     * 因为有注解@CachePut，所以每次都会执行方法
     */
    @Caching(
            cacheable = {
                    @Cacheable(/*value="employee",*/key = "#lastName")
            },
            put = {
                    @CachePut(/*value="employee",*/key = "#result.id"),
                    @CachePut(/*value="employee",*/key = "#result.email")
            }
    )
    public Employee getEmpByLastName(String lastName) {
        return employeeMapper.getEmpByLastName(lastName);
    }
}
