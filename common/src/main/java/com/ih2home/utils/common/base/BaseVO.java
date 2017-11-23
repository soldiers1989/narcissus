
package com.ih2home.utils.common.base;

import com.alibaba.fastjson.JSON;
import com.ih2home.utils.common.utils.JsonUtil;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 *
 *
 * @author 徐春华 2016年11月30日
 */
public abstract class BaseVO {
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static String toJson(Object obj, String... strings) {
        return JsonUtil.toJson(obj, strings);
    }
}