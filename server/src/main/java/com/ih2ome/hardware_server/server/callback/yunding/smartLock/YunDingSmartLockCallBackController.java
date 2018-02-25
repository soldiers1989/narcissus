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

    @RequestMapping(value="/setOAuthCode",produces = {"application/json"})
    public String setOAuthCode(HttpServletRequest request){
        String code = request.getParameter("code");
        String userId = request.getParameter("state");
        //第三方回调传userId
        if(StringUtils.isNotBlank(userId)){
            //用户授权code存redis，有效期4分30秒（文档中为5分钟，防止边界）
            CacheUtils.set(TOKEN_YUNDING_USER_CODE+"_"+userId,code, ExpireTime.FIVE_MIN.getTime()-30);
            CacheUtils.del(ACCESS_TOKEN_KEY+"_"+userId);
            CacheUtils.del(REFRESH_TOKEN_KEY+"_"+userId);
            return "<html><body>登陆已提交，正在跳转<img width=\"50px\" height=\"50px\" src=\"data:image/jpg;base64,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\"/></body></html>\t\t\t";

        }

        return structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"userid不存在");

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
