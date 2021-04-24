package com.springboot.cache.bean;

/**
 * @Classname Department
 * @Date 2021/4/24 15:49
 * @Created by FallingStars
 * @Description
 */
public class Department {
    private Integer id;
    private String departmentName;

    public Department(Integer id, String departmentName) {
        super();
        this.id = id;
        this.departmentName = departmentName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "Department [id=" + id + ", departmentName=" + departmentName + "]";
    }

}
