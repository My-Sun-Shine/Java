package com.crm.settings.domain;

/**
 * @Classname Dept
 * @Date 2020/3/21 21:23
 * @Created by Falling Stars
 * @Description 部门
 */
public class Dept {
    private String no;
    private String manager;
    private String name;
    private String phone;
    private String description;

    public Dept(String no, String manager, String name, String phone, String description) {
        this.no = no;
        this.manager = manager;
        this.name = name;
        this.phone = phone;
        this.description = description;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "no='" + no + '\'' +
                ", manager='" + manager + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
