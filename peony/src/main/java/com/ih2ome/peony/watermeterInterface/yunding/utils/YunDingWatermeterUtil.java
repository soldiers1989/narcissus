package com.ih2ome.peony.watermeterInterface.yunding.utils;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ExpireTime;
import com.ih2ome.common.utils.CacheUtils;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.common.utils.MyConstUtils;
import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/27
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class YunDingWatermeterUtil {
    private static final String USER_NAME = "7b7c10ebdec490d4e0d5850c";
    private static final String PASSWORD = "b7019a28af49f61dcda9e6b4b42b4aca";
    private static final String TOKEN_KEY = "YUN_DING_WATERMETER_TOKEN";
    private static final String UID_KEY = "YUN_DING_WATERMETER_UID";
    private static final String EXPRIES_TIME = "YUN_DING_WATERMETER_EXPRIES_TIME";
    private static final String SECRET = "";
    private static final String BASE_URL = "https://lockapi.dding.net/openapi/v1";
//    private static final String BASE_URL = "http://dev-lockapi.dding.net:8090/openapi/v1";
//    private static final String BASE_URL = "http://yundinghometest.dding.net:8088/openapi/v1";
    private static final String VERSION_VALUE = "0116010101";

    /**
     * 获取签名
     *
     * @param url
     * @return
     */
    public static String generateSign(String url, Map map) {

        return MyConstUtils.md5(url + sign1(map));
    }

    /**
     * 构建完整请求链接
     *
     * @param uri
     * @return
     */
    public static String generateParam(String uri,Map map) {

        String sign = generateSign(uri,map);
        String url = uri + "&signValue=" + sign;
        return url;
    }


    /**
     * 从第三方获取token和uid
     *
     * @return
     */
    private static Map<String, Object> getTokenByThrid() throws WatermeterException {

        Map<String, String> map = new HashMap<>();
        String uri = BASE_URL + "/access_token";

        //String url = generateParam(uri,map);
        JSONObject json = new JSONObject();
        json.put("client_id", USER_NAME);
        json.put("client_secret", PASSWORD);
        //json.put("signValue",generateSign(uri,map));

        String res = HttpClientUtil.doPost(uri,json);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        } catch (Exception e) {
            throw new WatermeterException("json格式解析错误" + e.getMessage());
        }
        //System.out.println(resJson);
        String code = String.valueOf(resJson.get("ErrNo"));
        if (!code.equals("0")) {
            throw new WatermeterException("第三方请求失败/n" + resJson.get("ErrMsg"));
        }

        String access_token = resJson.get("access_token").toString();
        int expires_time = resJson.getIntValue("expires_time");
        Map<String, Object> map2 = new HashMap<>();
        System.out.println("expires_time:"+expires_time+"----"+System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        map2.put(EXPRIES_TIME, (int)(expires_time - (System.currentTimeMillis()/1000)));
        map2.put(TOKEN_KEY, access_token);
        return map2;


    }

    /**
     * 获取token和uid，先从redis中取，若redis中的过期从第三方接口中取
     *
     * @return
     */
    public static String getToken() throws WatermeterException {
        String token = CacheUtils.getStr(TOKEN_KEY);
        //String uid = CacheUtils.getStr(UID_KEY);
        Map<String, Object> map = null;
        if (StringUtils.isBlank(token)) {
            map = getTokenByThrid();
            token = (String) map.get(TOKEN_KEY);
            Integer expires_time = (Integer) map.get(EXPRIES_TIME);
            CacheUtils.set(TOKEN_KEY, token, ExpireTime.HALF_AN_HOUR);
            // CacheUtils.set(UID_KEY,uid,0);
        }
        //CacheUtils.set(TOKEN_KEY, token, 1);
        return token;
    }


    /**
     * map字典排序
     * @param map
     * @return
     */
    public static String sign1(Map map) {

        Collection<String> keyset= map.keySet();

        List list=new ArrayList<String>(keyset);

        Collections.sort(list);
        //这种打印出的字符串顺序和微信官网提供的字典序顺序是一致的
        String sign = null;
        for(int i=0;i<list.size();i++){
            if (map.get(list.get(i)) != null && map.get(list.get(i)) != "") {
                sign = list.get(i) + "=" + map.get(list.get(i));
            }
        }

        return sign;
    }

}
