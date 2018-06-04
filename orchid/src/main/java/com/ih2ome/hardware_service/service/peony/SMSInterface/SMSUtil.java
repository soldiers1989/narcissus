package com.ih2ome.hardware_service.service.peony.SMSInterface;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.utils.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lucius
 * create by 2018/1/3
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class SMSUtil {
    public static boolean sendTemplateText(String baseUrl,String templateCode
            , String phoneNumber, JSONObject data, Integer interval ){
        JSONObject req = new JSONObject();
        req.put("number",phoneNumber);
        req.put("template",templateCode);
        req.put("data",data);
        req.put("interval",interval);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String result = HttpClientUtil.doPostJson(baseUrl+"/text", JSONObject.toJSONString(req),headers);
        JSONObject res = JSONObject.parseObject(result);
        return res.getString("retcode").equals("0");
    }
}
