package com.yzz.lr.util;


import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author zhizhuang.yang
 * @date 2017年9月13日
 * @version 1.0.0
 * @description JSON工具类
 */
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public abstract class JsonUtils {


    /**
     * 实体的值转成JSON对象的值(不带斜杠)
     *
     * @param source
     */
    public static JSONObject entityToJSON(Object source) {
        JSONObject dest = new JSONObject();

        Class clzss = source.getClass();

        dest = doEntityToJSON(source,clzss,dest);

        return dest;
    }

    /**
     * 递归执行实体转换JSON
     * @param source
     * @param clzss
     * @param dest
     * @return
     */
    public static JSONObject doEntityToJSON(Object source,Class clzss,JSONObject dest){
        Field[] fields = clzss.getDeclaredFields();
        if(fields.length==0){
            try{
                if(clzss.getSuperclass().getDeclaredFields().length==0)
                    return dest;
            }catch (Exception e){
                return dest;
            }
        }
        try {
            for (Field field : fields) {
                dest.put(field.getName(), getFieldValue(source,clzss, field.getName()));
            }
            dest = doEntityToJSON(source,clzss.getSuperclass(),dest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dest;
    }

    /**
     * 获取字段的值
     *
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object source,Class clzss, String fieldName) {

        StringBuilder sb = new StringBuilder();

        //将字段首字母大写
        String firstWord = fieldName.substring(0, 1).toUpperCase();
        sb.append(firstWord);
        sb.append(fieldName.substring(1, fieldName.length()));

        final String methodName = "get" + sb.toString();

        Method[] methods = clzss.getDeclaredMethods();
        try {
            for (Method method : methods) {
                // 调用对应的方法
                if (methodName.equals(method.getName())) {
                    return method.invoke(source, new Object[]{});
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;

    }
}
