package com.springbootweb.mapper;

import com.springbootweb.entity.Department;
import org.apache.ibatis.annotations.*;

/**
 * @Classname DepartmentMapper
 * @Date 2021/4/14 21:04
 * @Created by FallingStars
 * @Description 指定这是一个操作数据库的mapper
 */
@Mapper
public interface DepartmentMapper {
    @Select("select * from department where id=#{id}")
    public Department getDeptById(Integer id);

    @Delete("delete from department where id=#{id}")
    public int deleteDeptById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into department(departmentName) values(#{departmentName})")
    public int insertDept(Department department);

    @Update("update department set departmentName=#{departmentName} where id=#{id}")
    public int updateDept(Department department);
}
