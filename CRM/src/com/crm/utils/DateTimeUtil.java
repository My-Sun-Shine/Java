package com.crm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Classname DateTimeUtil
 * @Date 2020/3/21 22:22
 * @Created by Falling Stars
 * @Description 获取时间工具
 */
public class DateTimeUtil {

    public static String getSysTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();
        String dateStr = sdf.format(date);

        return dateStr;

    }

}
