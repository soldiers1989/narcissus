package com.ih2ome.peony.ammeterInterface.powerBee.util;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ExpireTime;
import com.ih2ome.common.utils.CacheUtils;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.common.utils.MyConstUtils;
import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;

import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/27
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class PowerBeeAmmeterUtil {
    private static final String USER_NAME = "13524921943";
    private static final String PASSWORD = "36954653";
    private static final String TOKEN_KEY = "POWER_BEE_AMMETER_TOKEN";
    private static final String UID_KEY = "POWER_BEE_AMMETER_UID";
    private static final String SECRET = "";
    private static final String BASE_URL = "http://smartammeter.zg118.com:8001";
    private static final String VERSION_VALUE = "0116010101";

    /**
     * 获取签名
     * @param url
     * @return
     */
    public static String generateSign(String url,String randomNum,Long timestamp){
        return MyConstUtils.md5(url+"timestamp="+timestamp+"&random="+randomNum+SECRET);
    }

    /**
     * 构建完整请求链接
     * @param uri
     * @return
     */
    public static String generateParam(String uri){
        String randomNum = MyConstUtils.getUUID();
        Long timestamp = System.currentTimeMillis();
        String sign = generateSign(uri,randomNum,timestamp);
        String url = uri + "?timestamp="+timestamp+"&signature="+sign;
        return url;
    }
    /**
     * 从第三方获取token和uid
     * @return
     */
    private static Map <String,String> getTokenByThrid() throws AmmeterException {

        Map <String,String> map = new HashMap<>();
        String uri = BASE_URL+"/user/login/"+USER_NAME+"/"+PASSWORD;
        String url = generateParam(uri);
        String res = HttpClientUtil.doGet(url);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            throw new AmmeterException("json格式解析错误"+e.getMessage());
        }
        String code = String.valueOf(resJson.get("Code"));
        if(!code.equals("0")){
            throw new AmmeterException("第三方请求失败/n"+resJson.get("Message"));
        }
        JSONObject data = JSONObject.parseObject(resJson.get("Data").toString());
        String uid = data.get("Uuid").toString();
        String expand = resJson.get("Expand").toString();
        map.put(UID_KEY,uid);
        map.put(TOKEN_KEY,expand);
        return map;


    }

    /**
     * 获取token和uid，先从redis中取，若redis中的过期从第三方接口中取
     * @return
     */
    public static Map <String,String> getToken() throws AmmeterException {
        String token = CacheUtils.getStr(TOKEN_KEY);
        String uid = CacheUtils.getStr(UID_KEY);
        Map <String,String> map = null;
        if (StringUtils.isBlank(token)||StringUtils.isBlank(uid)){
            map = getTokenByThrid();
            token = map.get(TOKEN_KEY);
            uid = map.get(UID_KEY);
            CacheUtils.set(TOKEN_KEY,token, ExpireTime.FIFTY_EIGHT_MIN);
            CacheUtils.set(UID_KEY,uid,ExpireTime.FIFTY_EIGHT_MIN);
        }else{
            map = new HashMap<>();
            map.put(TOKEN_KEY,token);
            map.put(UID_KEY,uid);
        }
        Map <String,String> header = new HashMap<>();
        header.put("uid",map.get(UID_KEY));
        header.put("token",map.get(TOKEN_KEY));
        header.put("version",VERSION_VALUE);
        return header;
    }


}
