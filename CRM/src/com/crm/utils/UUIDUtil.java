package com.crm.utils;

import java.util.UUID;

/**
 * @Classname UUIDUtil
 * @Date 2020/3/20 23:27
 * @Created by Falling Stars
 * @Description 获取UUID码
 */
public class UUIDUtil {

    public static String getUUID() {

        return UUID.randomUUID().toString().replaceAll("-", "");

    }

}
