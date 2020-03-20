package com.crm.settings.domain;

/**
 * @Classname DicType
 * @Date 2020/3/20 21:56
 * @Created by Falling Stars
 * @Description 字典类型表
 */
public class DicType {
    private String code;
    private String name;
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
