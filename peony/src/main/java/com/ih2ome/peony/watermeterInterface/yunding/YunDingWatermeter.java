package com.ih2ome.peony.watermeterInterface.yunding;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.peony.ammeterInterface.powerBee.util.PowerBeeAmmeterUtil;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.peony.watermeterInterface.vo.AddHomeVo;
import com.ih2ome.peony.watermeterInterface.yunding.utils.YunDingWatermeterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YunDingWatermeter implements IWatermeter {

    private static final String TOKEN_KEY = "YUN_DING_WATERMETER_TOKEN";
    private static final String UID_KEY = "YUN_DING_WATERMETER_UID";
    private static final String EXPRIES_TIME = "YUN_DING_WATERMETER_EXPRIES_TIME";
    private static final String BASE_URL = "https://lockapi.dding.net/openspi/v1";
    private static final Logger Log = LoggerFactory.getLogger(YunDingWatermeter.class);

    @Override
    public String findHomeState(String home_id) throws WatermeterException {
        Log.info("查询房源");
        Log.info("房源id："+home_id);
        JSONObject json= new JSONObject();
        json.put("access_token",YunDingWatermeterUtil.getToken());
        json.put("home_id",home_id);

        String uri = BASE_URL+"/find_home_state";
        //String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doPostSSL(uri,(Object) json);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("json格式解析错误",e);
            throw new WatermeterException("json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败/n"+msg);
            throw new WatermeterException("第三方请求失败/n"+msg);
        }
        return res;
    }

    /**
     * 添加房源
     * @param home
     */
    @Override
    public void addHome(AddHomeVo home) throws WatermeterException {
        Log.info("添加房源");
        Log.info("房源信息："+home.toString());
        home.setAccess_token(YunDingWatermeterUtil.getToken());

        String uri = BASE_URL+"/add_home";
        String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doPostSSL(url,home);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("json格式解析错误",e);
            throw new WatermeterException("json格式解析错误"+e.getMessage());
        }
        String code = resJson.get("Code").toString();
        if(!code.equals("0")){
            String msg = resJson.get("Message").toString();
            Log.error("第三方请求失败/n"+msg);
            throw new WatermeterException("第三方请求失败/n"+msg);
        }
    }
}
