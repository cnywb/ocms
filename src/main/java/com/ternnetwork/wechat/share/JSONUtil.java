package com.ternnetwork.wechat.share;



import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述:json 工具类
 *
 * @author yangkui create on 2016-09-14.
 * @since 1.0
 */
public class JSONUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换为list集合格式
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List toListObject(String json, Class<T> clazz) {
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);

            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String objectToJson(Object o){
        ObjectMapper om = new ObjectMapper();
        Writer w = new StringWriter();
        String json = null;
        try {
            om.writeValue(w, o);
            json = w.toString();
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

}