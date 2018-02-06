package com.ih2ome.hardware_server.server.callback.yunding.smartLock;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.enums.ExpireTime;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.common.utils.CacheUtils;
import com.ih2ome.common.utils.StringUtils;
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

    final static String TOKEN_YUNDING_USER_CODE = "yunding_user_code_token";

    private static String CALLBACK_PATH="http://rose.ih2ome.cn/api/yunDing/callBack/smartLock";

    @RequestMapping(value="/setOAuthCode",produces = {"application/json"})
    public String setOAuthCode(HttpServletRequest request){
        String code = request.getParameter("code");
        String userId = request.getParameter("state");
        //第三方回调传userId
        if(StringUtils.isNotBlank(userId)){
            //用户授权code存redis，有效期4分30秒（文档中为5分钟，防止边界）
            CacheUtils.set(TOKEN_YUNDING_USER_CODE+"_"+userId,code, ExpireTime.FIVE_MIN.getTime()-30);
            return structureSuccessResponseVO(null,new Date().toString(),"授权成功");

        }

        return structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"userid不存在");

    }

    @RequestMapping(value="/smartLock",method = RequestMethod.POST,produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Object> yundingSmartLockCallBack(@RequestBody CallbackRequestVo apiRequestVO){
        Log.info("云丁门锁回调接口开始:{}",apiRequestVO.toString());
        String sign = apiRequestVO.getSign();
        boolean flag=checkSign(sign,apiRequestVO);
        if(!flag){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("parameter error");
        }
        String event = apiRequestVO.getEvent();
        switch (event){
            case "batteryAlarm":
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "clearBatteryAlarm":
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "brokenAlarm":
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "wrongPwdAlarm":
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "pwdSync":
                //TODO:本次没有密码同步需求，暂时不做实现
                break;
            case "pwdAddLocal":
                updateSmartLockPassword(apiRequestVO);
                break;
            case "pwdDelLocal":
                deleteSmartLockPassword(apiRequestVO);
                break;
            case "pwdUpdateLocal":
                updateSmartLockPassword(apiRequestVO);
                break;
            case "lockerOpenAlarm":
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "clearCenterOfflineAlarm":
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "clearLockOfflineAlarm":
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "centerOfflineAlarm":
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "lockOfflineAlarm":
                saveSmartLockAlarm(apiRequestVO);
                break;
            case "batteryAsync":
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
        String battery = apiRequestVO.getDetail().getJSONObject("detail").getString("battery");
        String uuid = apiRequestVO.getUuid();
        lockInfoVo.setUuid(uuid);
        lockInfoVo.setRemainingBattery(battery);
        smartLockService.updateBatteryInfo(lockInfoVo);

    }

}
