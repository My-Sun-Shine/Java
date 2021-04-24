package com.springboot.cache.mapper;

import com.springboot.cache.bean.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Classname DepartmentMapper
 * @Date 2021/4/24 15:56
 * @Created by FallingStars
 * @Description
 */
@Mapper
public interface DepartmentMapper {
    @Select("SELECT * FROM department WHERE id = #{id}")
    public Department getDeptById(Integer id);
}
