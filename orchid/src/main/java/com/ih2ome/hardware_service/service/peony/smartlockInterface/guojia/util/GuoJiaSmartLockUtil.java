package com.ih2ome.hardware_service.service.peony.smartlockInterface.guojia.util;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.utils.*;
import com.ih2ome.hardware_service.service.peony.smartlockInterface.exception.SmartLockException;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private static final String PASSWORD = "dengkai";
    //访问凭证
    private static final String TOKEN_KEY = "GUOJIA_SMARTLOCK_TOKEN";
    //凭证的有效时长
    private static final String EXPRIES_KEY = "GUOJIA_SMARTLOCK_EXPRIES_SECOND";
    //果家平台地址
    private static final String BASE_URL = "http://ops.huohetech.com:80";
    //接口版本号，默认1.0
    private static final String VERSION_VALUE = "1.0";
    //密钥
    private static final String SECURITY_KEY = "Qwf5GuWz";


    /**
     * 从第三方获取token和expires_second(有效时长)
     *
     * @return
     */
    public static Map<String, String> getTokenByThrid() throws SmartLockException {
        Map<String, String> headers = new HashMap<>();
        String uri = BASE_URL + "/login";
        JSONObject body = new JSONObject();
        headers.put("Content-Type", "application/json");
        headers.put("s_id", MyConstUtils.getUUID());
        headers.put("version", VERSION_VALUE);
        body.put("password", desEncode(PASSWORD));
        body.put("account", USER_NAME);
        String result = HttpClientUtil.doPostJson(uri, body.toJSONString(), headers);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(result);
        } catch (Exception e) {
            throw new SmartLockException("json格式解析错误" + e.getMessage());
        }
        String rlt_code = resJson.getString("rlt_code");
        if (!rlt_code.equals("HH0000")) {
            throw new SmartLockException("第三方请求失败/n" + resJson.get("rlt_msg"));
        }
        JSONObject dataJson = JSONObject.parseObject(resJson.get("data").toString());
        String access_token = dataJson.get("access_token").toString();
        Integer expires_second = dataJson.getIntValue("expires_second") - 600;
        Map<String, String> map = new HashMap<>();
        map.put(TOKEN_KEY, access_token);
        map.put(EXPRIES_KEY, expires_second.toString());
        return map;
    }

    /**
     * 获取access_token，先从redis中取，若redis中的过期从第三方接口中取
     *
     * @return
     */
    public static Map<String, String> getToken() throws SmartLockException {
        String accessToken = CacheUtils.getStr(TOKEN_KEY);
        Map<String, String> map = null;
        if (StringUtils.isBlank(accessToken)) {
            //从第三方中获取access_token,expires_second
            map = getTokenByThrid();
            accessToken = map.get(TOKEN_KEY);
            String expiresSecond = map.get(EXPRIES_KEY);
            CacheUtils.set(TOKEN_KEY, accessToken, Integer.valueOf(expiresSecond));
        } else {
            map = new HashMap<>();
            map.put(TOKEN_KEY, accessToken);
        }
        return map;
    }

    /**
     * 获取请求头信息（包括version,s_id,access_token）
     *
     * @return
     * @throws SmartLockException
     */
    public static Map<String, String> getHeaders() throws SmartLockException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("version", VERSION_VALUE);
        headers.put("s_id", MyConstUtils.getUUID());
        headers.put("access_token", getToken().get(TOKEN_KEY));
        return headers;
    }

    /**
     * 将密码进行des加密（果家）
     *
     * @param password
     * @return
     * @throws Exception
     */
    public static String desEncode(String password) throws SmartLockException {
        String securityKey = SECURITY_KEY;
        int length = securityKey.length();
        if (length >= 8) {
            securityKey = securityKey.substring(0, length);
        } else {
            for (int i = 0; i < (8 - length); i++) {
                securityKey += "0";
            }
        }
        byte[] bytes = new byte[0];
        try {
            byte[] data = password.getBytes("utf-8");
            byte[] key = securityKey.getBytes("utf-8");
            bytes = DesUtil.encrypt(data, key);
        } catch (Exception e) {
            throw new SmartLockException("des加密错误");
        }
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for (byte b : bytes) {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() == 1)// 每个字节8为，转为16进制标志，2个16进制位
            {
                tmp = "0" + tmp;
            }
            sb.append(tmp);
        }
        return sb.toString();
    }

    /**
     * 时间戳转时间
     * @param s
     * @return
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}

