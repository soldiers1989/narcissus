package com.ih2ome.peony.watermeterInterface.yunding.utils;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.utils.CacheUtils;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;

import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/27
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class YunDingWatermeterUtil {
    private static final String USER_NAME = "13918541485";
    private static final String PASSWORD = "cihty5sj29";
    private static final String TOKEN_KEY = "YUN_DING_WATERMETER_TOKEN";
    private static final String UID_KEY = "YUN_DING_WATERMETER_UID";
    private static final String EXPRIES_TIME = "YUN_DING_WATERMETER_EXPRIES_TIME";
    private static final String SECRET = "";
    private static final String BASE_URL = "https://dev-lockapi.dding.net:8090/openspi/v1";
    private static final String VERSION_VALUE = "0116010101";


    /**
     * 从第三方获取token和uid
     * @return
     */
    private static Map <String,String> getTokenByThrid() throws WatermeterException {

        Map<String,String> map = new HashMap<>();
        String url = BASE_URL+"/access_token";
        JSONObject json=new JSONObject();
        json.put("clien_id",USER_NAME);
        json.put("clien_secret",PASSWORD);
        String res = HttpClientUtil.doPostSSL(url,json);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            throw new WatermeterException("json格式解析错误"+e.getMessage());
        }
        String code = String.valueOf(resJson.get("ErrNo"));
        if(!code.equals("0")){
            throw new WatermeterException("第三方请求失败/n"+resJson.get("ErrMsg"));
        }

        String access_token = resJson.get("access_token").toString();
        int expires_time = resJson.getIntValue("expires_time");
        map.put(EXPRIES_TIME,expires_time+"");
        map.put(TOKEN_KEY,access_token);
        return map;


    }

    /**
     * 获取token和uid，先从redis中取，若redis中的过期从第三方接口中取
     * @return
     */
    public static Map <String,String> getToken() throws WatermeterException {
        String token = CacheUtils.getStr(TOKEN_KEY);
        //String uid = CacheUtils.getStr(UID_KEY);
        Map <String,String> map = null;
        if (StringUtils.isBlank(token)){
            map = getTokenByThrid();
            token = map.get(TOKEN_KEY);
            int expires_time = Integer.parseInt(map.get(EXPRIES_TIME));
            CacheUtils.set(TOKEN_KEY,token, expires_time);
           // CacheUtils.set(UID_KEY,uid,ExpireTime.FIFTY_EIGHT_MIN);
        }else{
            map = new HashMap<>();
            map.put(TOKEN_KEY,token);
            //map.put(UID_KEY,uid);
        }
        Map <String,String> header = new HashMap<>();
        //header.put("uid",map.get(UID_KEY));
        header.put("token",map.get(TOKEN_KEY));
        //header.put("version",VERSION_VALUE);
        return header;
    }


}
