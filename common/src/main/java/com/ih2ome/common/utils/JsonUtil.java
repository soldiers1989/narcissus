package com.ih2ome.common.utils;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.JSONSerializerMap;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;

import java.util.Date;
import java.util.HashMap;

public class JsonUtil {
    /**
     * 将一个 Obj 里面的数据按需组织成 json , 支持别名
     * @param obj
     * @param strings
     * @return
     *
     * @author 徐春华 2017年1月19日
     *
     */
    public static String toJson(Object obj, String... strings) {
        HashMap<String, String> singletonMap = new HashMap<String, String>();
        for (String key : strings) {
            if (key.contains(":")) {
                singletonMap.put(key.substring(0, key.indexOf(":")), key.substring(key.indexOf(":") + 1));

            } else {
                singletonMap.put(key, key);
            }
        }
        JSONSerializerMap mapping = new JSONSerializerMap();
        mapping.put(obj.getClass(), new JavaBeanSerializer(obj.getClass(), singletonMap));
        JSONSerializer serializer = new JSONSerializer(mapping);
        serializer.write(obj);
        return serializer.toString();
    }

    public static String getJsonFormatString(String key, String value, boolean hasCommas) {
        if (value == null || "null".equals(value)) {
            value = "";
        }
        return "\"" + key + "\":\"" + value + "\"" + (hasCommas ? "," : "");
    }

    public static String getJsonFormatString(String key, Integer value, boolean hasCommas) {
        return getJsonFormatString(key, "" + value, hasCommas);
    }

    public static String getJsonFormatString(String key, Date value, boolean hasCommas) {
        return getJsonFormatString(key, "" + value, hasCommas);
    }

    /**
     * 替换不规则字符串
     *
     * @param jsonstr
     * @return
     */
    public static String escapeFormatString(String jsonstr) {
        String[] searchString = { "\r", "\n", "\t" };
        String[] replacement = { "", "", "" };
        return escapeFormatString(jsonstr, searchString, replacement);
    }

    public static String escapeFormatString(String jsonstr, String[] searchString, String[] replacement) {
        for (int i = 0; i < searchString.length; i++) {
            jsonstr = StringUtils.replace(jsonstr, searchString[i], replacement[i]);
        }
        return jsonstr;
    }

}
