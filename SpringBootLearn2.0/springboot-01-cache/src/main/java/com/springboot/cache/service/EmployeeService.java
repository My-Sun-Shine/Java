package com.springboot.cache.service;

import com.springboot.cache.bean.Employee;
import com.springboot.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Classname EmployeeService
 * @Date 2021/4/24 16:02
 * @Created by FallingStars
 * @Description
 */
@Service
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
     *
     * @param id
     * @return
     */
    @Cacheable(value = {"employee"}, key = "#id")
    public Employee getEmp(Integer id) {
        System.out.println("查询" + id + "号员工");
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
    }

    public Employee updateEmp(Employee employee) {
        System.out.println("updateEmp:" + employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }

    public void deleteEmp(Integer id) {
        System.out.println("deleteEmp:" + id);
        employeeMapper.deleteEmpById(id);
    }

    public Employee getEmpByLastName(String lastName) {
        return employeeMapper.getEmpByLastName(lastName);
    }
}
