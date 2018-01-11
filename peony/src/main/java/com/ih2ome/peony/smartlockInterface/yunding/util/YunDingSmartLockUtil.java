package com.ih2ome.peony.smartlockInterface.yunding.util;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.utils.CacheUtils;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sky
 * @create 2018/01/11
 * @email sky.li@ixiaoshuidi.com
 **/
public class YunDingSmartLockUtil {
    //云丁客户端账号
    private static final String USER_NAME = "7b7c10ebdec490d4e0d5850c";
    //云丁客户端密码
    private static final String PASSWORD = "b7019a28af49f61dcda9e6b4b42b4aca";
    //访问凭证
    private static final String TOKEN_KEY = "YUNDING_SMARTLOCK_TOKEN";
    //凭证的有效时长
    private static final String EXPRIES_KEY = "YUNDING_SMARTLOCK_EXPRIES_TIME";
    //云丁平台地址
    private static final String BASE_URL = "https://lockapi.dding.net/openapi/v1";

    /**
     * 从第三方获取access_token和expires_time(有效时长)
     *
     * @return
     * @throws SmartLockException
     */
    public static Map<String, Object> getTokenByThrid() throws SmartLockException {
        Map<String, String> map = new HashMap<>();
        String uri = BASE_URL + "/access_token";
        JSONObject json = new JSONObject();
        json.put("client_id", USER_NAME);
        json.put("client_secret", PASSWORD);
        String res = HttpClientUtil.doPost(uri, json);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        } catch (Exception e) {
            throw new SmartLockException("json格式解析错误" + e.getMessage());
        }
        String code = String.valueOf(resJson.get("ErrNo"));
        if (!code.equals("0")) {
            throw new SmartLockException("第三方请求失败/n" + resJson.get("ErrMsg"));
        }
        //获取访问凭证
        String access_token = resJson.get("access_token").toString();
        //获取到期时间
        Long expires_time = resJson.getLongValue("expires_time");
        Map<String, Object> map2 = new HashMap<>();
        map2.put(EXPRIES_KEY, (Long) (expires_time - (System.currentTimeMillis() / 1000)));
        map2.put(TOKEN_KEY, access_token);
        return map2;
    }

    /**
     * 获取access_token
     *
     * @return
     * @throws SmartLockException
     */
    public static String getToken() throws SmartLockException {
        String token = CacheUtils.getStr(TOKEN_KEY);
        Map<String, Object> map = null;
        if (StringUtils.isBlank(token)) {
            map = getTokenByThrid();
            token = (String) map.get(TOKEN_KEY);
            Integer expires_time = (Integer) map.get(EXPRIES_KEY);
            CacheUtils.set(TOKEN_KEY, token, expires_time);
        }
        return token;
    }

//    public static void main(String[] args) throws SmartLockException {
//        getTokenByThrid();
//    }

}
