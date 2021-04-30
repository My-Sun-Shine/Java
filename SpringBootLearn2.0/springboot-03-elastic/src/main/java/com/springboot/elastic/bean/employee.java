package com.springboot.elastic.bean;

import java.util.List;

/**
 * @Classname employee
 * @Date 2021/4/28 22:57
 * @Created by FallingStars
 * @Description
 */
public class employee {
    private String firstName;
    private String lastName;
    private Integer age;
    private String about;
    private List<String> interests;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}
