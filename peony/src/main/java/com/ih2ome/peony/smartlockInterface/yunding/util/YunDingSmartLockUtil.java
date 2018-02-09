package com.ih2ome.peony.smartlockInterface.yunding.util;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.utils.CacheUtils;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sky
 * @create 2018/01/11
 * @email sky.li@ixiaoshuidi.com
 **/
public class YunDingSmartLockUtil {
    private static final Logger Log = LoggerFactory.getLogger(YunDingSmartLockUtil.class);
    /**
     * 云丁客户端账号
     */
    private static final String USER_NAME = "7b7c10ebdec490d4e0d5850c";
    /**
     * 云丁客户端密码
     */
    private static final String PASSWORD = "b7019a28af49f61dcda9e6b4b42b4aca";
    /**
     * 访问凭证
     */
    private static final String TOKEN_KEY = "YUNDING_SMARTLOCK_TOKEN";
    /**
     * 凭证的有效时长
     */
    private static final String EXPRIES_KEY = "YUNDING_SMARTLOCK_EXPRIES_TIME";
    /**
     * 云丁平台地址
     */
    private static final String BASE_URL = "https://lockapi.dding.net/openapi/v1";
    /**
     * 云丁开放平台地址
     */
    public static final String OPEN_BASE_URL = "https://open.dding.net/open/v1";
    /**
     * 云丁开放平台测试地址
     */
    public static final String OPEN_TEST_BASE_URL = "http://dev-gate.dding.net:7080/open/v1";
    /**
     * 云丁开放平台水滴身份标识
     */
    public static final String OPEN_CLIENT_ID = "shuidiguanjia";
    /**
     * 云丁开放平台水滴访问密钥
     */
    public static final String OPEN_SECRET = "b3a19f6ba2e99046582a9cb5de334de6";
    /**
     * 云丁开放平台固定植入
     */
    public static final String GRANT_TYPE = "authorization_code";

    public final static String TOKEN_YUNDING_USER_CODE = "yunding_user_code_token";

    public final static String ACCESS_TOKEN_KEY = "access_token_key";

    public final static String REFRESH_TOKEN_KEY = "refresh_token_key";


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

    /**
     * 获取授权码
     *
     * @param userId
     * @return
     * @throws SmartLockException
     */
    public static String getLicenseCode(String userId) throws SmartLockException {
        String code = CacheUtils.getStr(TOKEN_YUNDING_USER_CODE + "_" + userId);
        if (StringUtils.isEmpty(code)) {
            throw new SmartLockException("请先做授权");
        }
        return code;
    }

    /**
     * 从第三方获取accessToken
     *
     * @param userId
     * @return
     * @throws SmartLockException
     */
    public static String getAccessTokenFromThrid(String userId) throws SmartLockException {
        String url = OPEN_BASE_URL + "/oauth/token";
        //组装post参数
        Map<String, Object> req = new HashMap<>();
        req.put("client_id", OPEN_CLIENT_ID);
        req.put("client_secret", OPEN_SECRET);
        req.put("code",getLicenseCode(userId));
        req.put("grant_type", "authorization_code");
        //组装头部
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/x-www-form-urlencoded");

        String res;
        try {
            res = HttpClientUtil.doPost(url, req, header);

        } catch (Exception e) {
            throw new SmartLockException("门锁获取第三方accessToken失败", e);

        }

        JSONObject resJson = JSONObject.parseObject(res);
        System.out.println(resJson);
        String accessToken = resJson.getString("access_token");
        String expiresIn = resJson.getString("expires_in");
        String refreshToken = resJson.getString("refresh_token");
        CacheUtils.set(ACCESS_TOKEN_KEY + "_" + userId, accessToken, Integer.valueOf(expiresIn) - 3 * 60 * 1000);
        CacheUtils.set(REFRESH_TOKEN_KEY + "_" + userId, refreshToken, Integer.valueOf(expiresIn) - 3 * 60 * 1000);

        return accessToken;

    }


    /**
     * 刷新token
     *
     * @param userId
     * @return
     */
    public static String flushRefreshToken(String userId) throws SmartLockException {
        String refreshToken = CacheUtils.getStr(REFRESH_TOKEN_KEY + "_" + userId);
        if (StringUtils.isEmpty(refreshToken)) {
            return getAccessTokenFromThrid(userId);

        }
        String url = OPEN_BASE_URL + "/oauth/token";

        //组装post参数
        Map<String, Object> req = new HashMap<>();
        req.put("client_id", OPEN_CLIENT_ID);
        req.put("client_secret", OPEN_SECRET);
        req.put("refresh_token", refreshToken);
        req.put("grant_type", "authorization_code");

        //组装头部
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/x-www-form-urlencoded");

        String res;
        try {
            res = HttpClientUtil.doPost(url, req, header);

        } catch (Exception e) {
            throw new SmartLockException("门锁获取第三方accessToken失败", e);

        }

        JSONObject resJson = JSONObject.parseObject(res);
        System.out.println(resJson);
        String accessToken = resJson.getString("access_token");
        String expiresIn = resJson.getString("expires_in");
        refreshToken = resJson.getString("refresh_token");

        CacheUtils.set(ACCESS_TOKEN_KEY + "_" + userId, accessToken, Integer.valueOf(expiresIn) - 3 * 60 * 1000);
        CacheUtils.set(REFRESH_TOKEN_KEY + "_" + userId, refreshToken, Integer.valueOf(expiresIn) - 3 * 60 * 1000);

        return accessToken;
    }

    /**
     * 获取访问token
     *
     * @param userId
     * @return
     * @throws SmartLockException
     */
    public static String getAccessToken(String userId) throws SmartLockException {
        String accessToken = CacheUtils.getStr(ACCESS_TOKEN_KEY + "_" + userId);
        if (StringUtils.isNotBlank(accessToken)) {
            return accessToken;

        }

        accessToken = flushRefreshToken(userId);
        return accessToken;

    }


}
