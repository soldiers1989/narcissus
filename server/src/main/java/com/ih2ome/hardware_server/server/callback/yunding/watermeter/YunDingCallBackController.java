package com.ih2ome.hardware_server.server.callback;

import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_server.server.callback.help.YunDingCallBackHelp;

import com.ih2ome.hardware_service.service.service.GatewayBindService;
import com.ih2ome.hardware_service.service.service.GatewayService;
import com.ih2ome.sunflower.vo.thirdVo.yunDingCallBack.CallbackRequestVo;

import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;

import com.ih2ome.sunflower.vo.pageVo.enums.OnOffStatusEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 云丁回调接口
 */
@RestController
public class YunDingCallBackController extends BaseController {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private YunDingCallBackHelp yunDingCallBackHelp;

    @Autowired
    private GatewayService gatewayService;

    @Autowired
    private GatewayBindService gatewayBindService;

    /**
     * 水表回调接口
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/callback/watermeter/yunding",method = RequestMethod.POST,produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Object> watermeterAmountAsync(@RequestBody CallbackRequestVo apiRequestVO) {
        System.out.println(apiRequestVO.toString());
        Log.info("水表回调接口,apiRequestVO：{}",apiRequestVO.toString());
        //校验签名
        String sign = apiRequestVO.getSign();
        /*boolean flag=yunDingCallBackHelp.checkSign(sign,apiRequestVO);
        if(!flag){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("parameter error");
        }*/
        String event = apiRequestVO.getEvent();
        switch (event){
            case  "deviceInstall" :
                //绑定水表及水表网关设备事件
                try {
                    yunDingCallBackHelp.deviceInstallEvent(apiRequestVO);
                } catch (WatermeterException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
                }
                break;
            case "deviceReplace" :
                //替换水表网关事件
                //更新网关uuid
                gatewayService.updataGatewayUuid(apiRequestVO.getUuid(),apiRequestVO.getOld_uuid(),apiRequestVO.getTime(),apiRequestVO.getManufactory());
                break;
            case "deviceUninstall" :
                //设备解绑事件
                yunDingCallBackHelp.deviceUninstall(apiRequestVO);
                break;
            case "waterGatewayOfflineAlarm" :
                //水表网关离线事件
                yunDingCallBackHelp.waterGatewayOnOfflineAlarm(apiRequestVO, OnOffStatusEnum.ON_OFF_STATUS_ENUM_OFF_Line.getCode());
                break;
            case "waterGatewayOnlineAlarm" :
                //水表网关在线事件
                yunDingCallBackHelp.waterGatewayOnOfflineAlarm(apiRequestVO,OnOffStatusEnum.ON_OFF_STATUS_ENUM_ON_Line.getCode());
                break;
            case "watermeterAmountAsync" :
                //抄表读数同步
                yunDingCallBackHelp.watermeterAmountAsyncEvent(apiRequestVO);
                break;
        }
        return ResponseEntity.ok().body("ok");
    }



}
