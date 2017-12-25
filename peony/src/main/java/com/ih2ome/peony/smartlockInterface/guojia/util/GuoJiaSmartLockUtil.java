package com.ih2ome.peony.smartlockInterface.guojia.util;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ExpireTime;
import com.ih2ome.common.utils.CacheUtils;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.common.utils.MyConstUtils;
import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sky
 * @create 2017/12/25
 * @email sky.li@ixiaoshuidi.com
 **/
public class GuoJiaSmartLockUtil {
    //开放平台账号
    private static final String USER_NAME = "13918541485";
    //开放平台密码(加密后)9cb489dc31a4a79c
    private static final String PASSWORD = "9cb489dc31a4a79c";
    //访问凭证
    private static final String TOKEN_KEY = "GUOJIA_SMARTLOCK_TOKEN";
    //凭证的有效时长
    private static final String EXPRIES_SECOND = "GUOJIA_SMARTLOCK_EXPRIES_SECOND";
    //果家平台地址
    private static final String BASE_URL = "http://ops.huohetech.com:80";
    //接口版本号，默认1.0
    private static final String VERSION_VALUE = "1.0";
    //密钥
    private static final String SECURITY_KEY = "Qwf5GuWz";

    /**
     * 从第三方获取token和expires_second(有效时长)
     * @return
     */
    public static Map <String,String> getTokenByThrid() throws SmartLockException {
        Map <String,String> headers = new HashMap<>();
        String uri = BASE_URL+"/login";
        JSONObject body = new JSONObject();
        headers.put("Content-Type","application/json");
        headers.put("s_id",MyConstUtils.getUUID());
        headers.put("version",VERSION_VALUE);
        body.put("password",PASSWORD);
        body.put("account",USER_NAME);
        String result=HttpClientUtil.doPostJson(uri,body.toJSONString(),headers);
        JSONObject resJson=null;
        try{
            resJson = JSONObject.parseObject(result);
        }catch (Exception e){
            throw new SmartLockException("json格式解析错误" + e.getMessage());
        }
        String rlt_code = resJson.getString("rlt_code");
        if (!rlt_code.equals("HH0000")) {
            throw new SmartLockException("第三方请求失败/n" + resJson.get("rlt_msg"));
        }
        JSONObject dataJson=JSONObject.parseObject(resJson.get("data").toString());
        String access_token=dataJson.get("access_token").toString();
        Integer expires_second=dataJson.getIntValue("expires_second");
        Map<String, String> map= new HashMap<>();
        map.put(TOKEN_KEY,access_token);
        map.put(EXPRIES_SECOND,expires_second.toString());
        return map;
    }

    /**
     * 获取access_token，先从redis中取，若redis中的过期从第三方接口中取
     * @return
     */
    public static Map <String,String> getToken() throws SmartLockException {
        String accessToken = CacheUtils.getStr(TOKEN_KEY);
        String expiresSecond = CacheUtils.getStr(EXPRIES_SECOND);
        Map <String,String> map = null;
        if (StringUtils.isBlank(accessToken)||StringUtils.isBlank(expiresSecond)){
            //从第三方中获取access_token,expires_second
            map = getTokenByThrid();
            accessToken = map.get(TOKEN_KEY);
            expiresSecond = map.get(EXPRIES_SECOND);
            CacheUtils.set(TOKEN_KEY,accessToken, Integer.valueOf(expiresSecond));
        }else{
            map = new HashMap<>();
            map.put(TOKEN_KEY,accessToken);

        }
        return map;
    }


}
