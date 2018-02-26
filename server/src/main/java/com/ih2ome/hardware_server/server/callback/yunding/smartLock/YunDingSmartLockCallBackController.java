package com.ih2ome.hardware_server.server.callback.yunding.smartLock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.enums.ExpireTime;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.common.utils.CacheUtils;
import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.hardware_service.service.service.SmartLockGatewayService;
import com.ih2ome.hardware_service.service.service.SmartLockService;
import com.ih2ome.hardware_service.service.service.SmartLockWarningService;
import com.ih2ome.sunflower.entity.narcissus.SmartMistakeInfo;
import com.ih2ome.sunflower.vo.pageVo.enums.AlarmTypeEnum;
import com.ih2ome.sunflower.vo.pageVo.enums.SmartDeviceTypeEnum;
import com.ih2ome.sunflower.vo.pageVo.smartLock.LockInfoVo;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockPasswordVo;
import com.ih2ome.sunflower.vo.thirdVo.yunDingCallBack.CallbackRequestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/18
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@RestController
@RequestMapping("/yunDing/callBack")
@CrossOrigin
public class YunDingSmartLockCallBackController extends BaseController{

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SmartLockService smartLockService;

    @Autowired
    SmartLockWarningService smartLockWarningService;

    @Autowired
    SmartLockGatewayService smartLockGatewayService;

    final static String TOKEN_YUNDING_USER_CODE = "yunding_user_code_token";

    public final static String ACCESS_TOKEN_KEY = "access_token_key";

    public final static String REFRESH_TOKEN_KEY = "refresh_token_key";

    private static String CALLBACK_PATH="http://rose.ih2ome.cn/api/yunDing/callBack/smartLock";

    @RequestMapping(value="/setOAuthCode",produces = {"text/html"})
    public String setOAuthCode(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        String userId = request.getParameter("state");
        //第三方回调传userId
        if(StringUtils.isNotBlank(userId)){
            //用户授权code存redis，有效期4分30秒（文档中为5分钟，防止边界）
            CacheUtils.set(TOKEN_YUNDING_USER_CODE+"_"+userId,code, ExpireTime.FIVE_MIN.getTime()-30);
            CacheUtils.del(ACCESS_TOKEN_KEY+"_"+userId);
            CacheUtils.del(REFRESH_TOKEN_KEY+"_"+userId);
            response.addHeader("Content-Type","text/html");
            return "<html><body>登陆已提交，正在跳转<img width=\"50px\" height=\"50px\" src=\"data:image/jpg;base64,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\"/></body></html>\t\t\t";

        }

        return "<html><body>登录失败<img width=\"50px\"height=\"50px\"src=\"data:image/jpeg;base64,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\"></img></body></html>";

    }

    @RequestMapping(value="/smartLock",method = RequestMethod.POST,produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Object> yundingSmartLockCallBack(@RequestBody CallbackRequestVo apiRequestVO){
        Log.info("云丁门锁回调接口开始:{}",apiRequestVO.toString());
        String sign = apiRequestVO.getSign();
        boolean flag=checkSign(sign,apiRequestVO);
//        if(!flag){
//            Log.error("云丁回调参数错误");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("parameter error");
//        }
        //TODO:签名校验暂时取消
        String event = apiRequestVO.getEvent();
        if(StringUtils.isEmpty(event)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("parameter error");
        }
        switch (event){
            case "batteryAlarm":
                Log.info("低电量回调");
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "clearBatteryAlarm":
                Log.info("电量恢复回调");
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "brokenAlarm":
                Log.info("门锁被破坏报警");
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "wrongPwdAlarm":
                Log.info("密码输错三次报警");
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "pwdSync":
                //TODO:本次没有密码同步需求，暂时不做实现
                break;
            case "pwdAddLocal":
                Log.info("密码更新");
                updateSmartLockPassword(apiRequestVO);
                break;
            case "pwdDelLocal":
                Log.info("密码删除");
                deleteSmartLockPassword(apiRequestVO);
                break;
            case "pwdUpdateLocal":
                Log.info("密码更新");
                updateSmartLockPassword(apiRequestVO);
                break;
            case "lockerOpenAlarm":
                Log.info("门锁开启");
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "clearCenterOfflineAlarm":
                Log.info("网关在线");
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "clearLockOfflineAlarm":
                Log.info("门锁在线");
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "centerOfflineAlarm":
                Log.info("网关离线");
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "lockOfflineAlarm":
                Log.info("门锁离线");
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "batteryAsync":
                Log.info("电量同步");
                asyncBattery(apiRequestVO);
                break;
            case "deviceUninstall":
                Log.info("设备解绑");
                deviceUninstall(apiRequestVO);
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("parameter error");

        }

        Log.info("云丁门锁回调接口结束");
        return ResponseEntity.ok().body("ok");
    }

    /**
     * 校验签名
     * @param sign
     * @param apiRequestVO
     * @return
     */
    private boolean checkSign(String sign, CallbackRequestVo apiRequestVO) {
        Map<String,Object> map=new HashMap<>();
        map.put("event",apiRequestVO.getEvent());
        map.put("time",apiRequestVO.getTime());
        map.put("uuid",apiRequestVO.getUuid());
        map.put("old_uuid",apiRequestVO.getOld_uuid());
        map.put("manufactory",apiRequestVO.getManufactory());
        map.put("home_id",apiRequestVO.getHome_id());
        map.put("gateway_uuid",apiRequestVO.getGateway_uuid());
        map.put("room_id",apiRequestVO.getRoom_id());
        map.put("detail",JSONObject.toJSONString(apiRequestVO.getDetail()));

        String sign1 = getSign(map);
        return sign.equals(sign1);

    }

    /**
     * map字典排序
     *
     * @param map
     * @return
     */
    public static String getSign(Map map) {

        Collection<String> keyset= map.keySet();

        List list=new ArrayList<String>(keyset);

        Collections.sort(list);
        //这种打印出的字符串顺序和微信官网提供的字典序顺序是一致的
        String str = "";
        for(int i=0;i<list.size();i++){
            if (map.get(list.get(i)) != null && map.get(list.get(i)) != "") {
                str += list.get(i) + "=" + map.get(list.get(i))+"&";
            }
        }
        String stringA=str.substring(0,str.length()-1);
        String stringSignTemp=CALLBACK_PATH + stringA;
        String sign= DigestUtils.md5DigestAsHex(stringSignTemp.getBytes());
        return sign;
    }

    /**
     * 构建第三方与本地的映射map
     * @param thirdAlarmType
     * @return
     */
    private static AlarmTypeEnum getThirdAlarmNameMap(String thirdAlarmType){
        Map <String,AlarmTypeEnum> thirdAlarmNameMap = new HashMap<>();
        thirdAlarmNameMap.put("batteryAlarm",AlarmTypeEnum.YUN_DING_SMART_LOCK_EXCEPTION_TYPE_LOWER_POWER);
        thirdAlarmNameMap.put("clearBatteryAlarm",AlarmTypeEnum.YUN_DING_SMART_LOCK_EXCEPTION_TYPE_LOWER_POWER_RECOVER);
        thirdAlarmNameMap.put("brokenAlarm",AlarmTypeEnum.YUN_DING_SMART_LOCK_EXCEPTION_TYPE_BROKEN);
        thirdAlarmNameMap.put("wrongPwdAlarm",AlarmTypeEnum.YUN_DING_SMART_LOCK_EXCEPTION_WRONG_PWD);
        thirdAlarmNameMap.put("lockerOpenAlarm",AlarmTypeEnum.YUN_DING_SMART_LOCK_OPEN);
        thirdAlarmNameMap.put("centerOfflineAlarm",AlarmTypeEnum.YUN_DING_SMAR_LOCK_GATEWAY_OFFLINE);
        thirdAlarmNameMap.put("clearCenterOfflineAlarm",AlarmTypeEnum.YUN_DING_SMAR_LOCK_GATEWAY_ONLINE);
        thirdAlarmNameMap.put("clearLockOfflineAlarm",AlarmTypeEnum.YUN_DING_SMAR_LOCK_ONLINE);
        thirdAlarmNameMap.put("lockOfflineAlarm",AlarmTypeEnum.YUN_DING_SMAR_LOCK_OFFLINE);
        return thirdAlarmNameMap.get(thirdAlarmType);
    }

    /**
     * 保存报警信息
     * @param apiRequestVO
     */
    private void saveSmartLockAlarm(CallbackRequestVo apiRequestVO){
        String [] lockAlarmName = {"batteryAlarm","clearBatteryAlarm","lockerOpenAlarm","clearLockOfflineAlarm","lockOfflineAlarm","brokenAlarm"};
        List<String> lockAmarmNameist=Arrays.asList(lockAlarmName);
        SmartMistakeInfo smartMistakeInfo = new SmartMistakeInfo();
        smartMistakeInfo.setUuid(apiRequestVO.getUuid());
        smartMistakeInfo.setExceptionType(getThirdAlarmNameMap(apiRequestVO.getEvent()).getCode()+"");
        if(lockAmarmNameist.contains(apiRequestVO.getEvent())){
            smartMistakeInfo.setSmartDeviceType(SmartDeviceTypeEnum.YUN_DING_SMART_LOCK.getCode());
        }else{
            smartMistakeInfo.setSmartDeviceType(SmartDeviceTypeEnum.YUN_DING_SMART_LOCK_GATEWAY.getCode());
        }
        if("lockerOpenAlarm".equals(apiRequestVO.getEvent())){
            JSONObject detail = apiRequestVO.getDetail();
            String  passwordId = detail.getString("sourceid");
            smartMistakeInfo.setSmartLockPasswordId(passwordId);

        }
        smartLockWarningService.saveSmartLockAlarmInfo(smartMistakeInfo);

    }

    /**
     * 更新门锁密码
     * @param apiRequestVO
     */
    private void updateSmartLockPassword(CallbackRequestVo apiRequestVO){
        JSONObject password = apiRequestVO.getDetail().getJSONObject("password");
        LockPasswordVo passwordVo = new LockPasswordVo();
        passwordVo.setUuid(password.getString("id"));
        passwordVo.setPassword(password.getString("password"));
        smartLockService.updateLockPasswordCallBack(passwordVo);

    }

    /**
     * 删除门锁密码
     * @param apiRequestVO
     */
    private void deleteSmartLockPassword(CallbackRequestVo apiRequestVO){
        smartLockService.deleteLockPasswordCallBack(apiRequestVO.getDetail().getString("id"));
    }

    /**
     * 同步电量信息
     * @param apiRequestVO
     */
    private void asyncBattery(CallbackRequestVo apiRequestVO){
        LockInfoVo lockInfoVo = new LockInfoVo();
        Log.info(JSON.toJSON(apiRequestVO).toString());
        String battery = apiRequestVO.getDetail().getString("battery");
        String uuid = apiRequestVO.getUuid();
        lockInfoVo.setUuid(uuid);
        lockInfoVo.setRemainingBattery(battery);
        smartLockService.updateBatteryInfo(lockInfoVo);

    }

    /**
     * 解绑设备
     * @param apiRequestVO
     */
    private void deviceUninstall(CallbackRequestVo apiRequestVO){
        JSONObject detail = apiRequestVO.getDetail().getJSONObject("detail");
        //网关
        if(detail.getString("type").equals("1")){
            smartLockGatewayService.uninstallSmartLockGateway(detail.getString("uuid"));
        }else if(detail.getString("type").equals("4")){
            smartLockService.uninstallSmartLock(detail.getString("uuid"));
        }
    }

}
