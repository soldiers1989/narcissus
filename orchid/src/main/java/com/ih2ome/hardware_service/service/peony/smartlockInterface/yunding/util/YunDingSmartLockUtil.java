package com.ih2ome.hardware_service.service.peony.smartlockInterface.yunding.util;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ExpireTime;
import com.ih2ome.common.utils.CacheUtils;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.hardware_service.service.dao.SmartLockDao;
import com.ih2ome.hardware_service.service.peony.smartlockInterface.exception.SmartLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sky
 * @create 2018/01/11
 * @email sky.li@ixiaoshuidi.com
 **/
@Component
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

    @Autowired
    private SmartLockDao smartLockDao;
    private static YunDingSmartLockUtil yunDingSmartLockUtil;

    @PostConstruct
    public void init() {
        yunDingSmartLockUtil = this;
        yunDingSmartLockUtil.smartLockDao = this.smartLockDao;
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
    public static String getAccessTokenFromThird(String userId) throws SmartLockException {
        String url = OPEN_BASE_URL + "/oauth/token";
        //组装post参数
        Map<String, Object> req = new HashMap<>();
        req.put("client_id", OPEN_CLIENT_ID);
        req.put("client_secret", OPEN_SECRET);
        req.put("code", getLicenseCode(userId));
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
        List<String> family = yunDingSmartLockUtil.smartLockDao.findFamilyUserIdList(userId);
        for (String item : family) {
            CacheUtils.set(ACCESS_TOKEN_KEY + "_" + item, accessToken, ExpireTime.ONE_MON.getTime() - 3 * 24 * 60 * 60);
            CacheUtils.set(REFRESH_TOKEN_KEY + "_" + item, refreshToken, ExpireTime.ONE_MON.getTime() - 3 * 24 * 60 * 60);
        }
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
        String url = OPEN_BASE_URL + "/oauth/token";

        //组装post参数
        Map<String, Object> req = new HashMap<>();
        req.put("client_id", OPEN_CLIENT_ID);
        req.put("client_secret", OPEN_SECRET);
        req.put("refresh_token", refreshToken);
        req.put("grant_type", "refresh_token");

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
        if (resJson.getString("ErrNo") != null && !"0".equals(resJson.getString("ErrNo")) || resJson.getIntValue("code") == 400) {
            throw new SmartLockException("登陆失败");
        }
        String accessToken = resJson.getString("access_token");
        String expiresIn = resJson.getString("expires_in");
        refreshToken = resJson.getString("refresh_token");
        List<String> family = yunDingSmartLockUtil.smartLockDao.findFamilyUserIdList(userId);
        for (String item : family) {
            CacheUtils.set(ACCESS_TOKEN_KEY + "_" + item, accessToken, ExpireTime.ONE_MON.getTime() - 3 * 24 * 60 * 60);
            CacheUtils.set(REFRESH_TOKEN_KEY + "_" + item, refreshToken, ExpireTime.ONE_MON.getTime() - 3 * 24 * 60 * 60);
        }
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
//        return "fcedd39b28affea896a65728a8be81593ebe95e8";
        Log.info("getAccessToken CacheKey:{}", ACCESS_TOKEN_KEY + "_" + userId);
        String accessToken = CacheUtils.getStr(ACCESS_TOKEN_KEY + "_" + userId);
        Log.info("getAccessToken CacheValues:{}", accessToken);
        if (StringUtils.isNotBlank(accessToken)) {
            return accessToken;

        }
        accessToken = getAccessTokenFromThird(userId);
        return accessToken;

    }
}
