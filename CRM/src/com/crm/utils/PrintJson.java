package com.crm.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Classname PrintJson
 * @Date 2020/3/20 23:27
 * @Created by Falling Stars
 * @Description 转换为JSON字符串进行输出
 */
public class PrintJson {

    /**
     * 将布尔值转换为json
     *
     * @param response
     * @param flag
     */
    public static void printJsonFlag(HttpServletResponse response, boolean flag) {

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", flag);

        ObjectMapper om = new ObjectMapper();
        printJsonObj(response, map);


    }

    /**
     * 将普通对象转换为json
     *
     * @param response
     * @param obj
     */
    public static void printJsonObj(HttpServletResponse response, Object obj) {

        ObjectMapper om = new ObjectMapper();
        try {
            String json = om.writeValueAsString(obj);
            response.getWriter().print(json);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}























