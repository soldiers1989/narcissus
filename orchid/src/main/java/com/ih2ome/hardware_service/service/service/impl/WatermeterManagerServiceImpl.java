package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ih2ome.hardware_service.service.dao.WatermeterManagerMapper;
import com.ih2ome.hardware_service.service.dao.WatermeterMapper;
import com.ih2ome.sunflower.vo.pageVo.enums.HouseStyleEnum;
import com.ih2ome.hardware_service.service.service.SynchronousHomeService;
import com.ih2ome.hardware_service.service.service.WatermeterManagerService;
import com.ih2ome.sunflower.vo.pageVo.watermeter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatermeterManagerServiceImpl implements WatermeterManagerService {
    @Autowired
    private WatermeterManagerMapper watermeterManagerMapper;

    @Autowired
    private WatermeterMapper watermeterMapper;

    @Autowired
    private SynchronousHomeService synchronousHomeService;

    private static final Logger Log = LoggerFactory.getLogger(WatermeterManagerServiceImpl.class);
    /**
     * 查询水表列表
     * @param watermeterWebListVo
     * @return
     */
    @Override
    public List<WatermeterWebListVo> watermeterWebListVoList(WatermeterWebListVo watermeterWebListVo) {
        Log.info("查询水表列表，watermeterWebListVo：{}",watermeterWebListVo.toString());
        if(watermeterWebListVo.getPage()!= null && watermeterWebListVo.getRows() != null){
            PageHelper.startPage(watermeterWebListVo.getPage(),watermeterWebListVo.getRows());
        }
        //分散式
        if(watermeterWebListVo.getType().equals(HouseStyleEnum.DISPERSED.getCode())){
            return watermeterManagerMapper.findHmWatermeterWebListVoList(watermeterWebListVo);
        } else if(watermeterWebListVo.getType().equals(HouseStyleEnum.CONCENTRAT.getCode())){
            //集中式
            return watermeterManagerMapper.findJzWatermeterWebListVoList(watermeterWebListVo);
        }else{
            return null;
        }
    }

    /**
     * 查询水表详情
     *
     * @param uuid
     * @param type
     * @return
     */
    @Override
    public WatermeterManagerDetailVO findWatermeterDetailByUuid(String uuid, String type) {
        Log.info("查询水表详情，uuid：{},type:{}",uuid,type);
        WatermeterManagerDetailVO WatermeterManagerDetailVO=null;
        //分散式
        if(type.equals(HouseStyleEnum.DISPERSED.getCode())){
            WatermeterManagerDetailVO= watermeterManagerMapper.selectHmWatermeterDetailByUuid(uuid);
         //集中式
        }else if(type.equals(HouseStyleEnum.CONCENTRAT.getCode())){
            WatermeterManagerDetailVO= watermeterManagerMapper.selectJzWatermeterDetailByUuid(uuid);
        }

        return WatermeterManagerDetailVO;
    }

    /**
     * 查询水表查表记录by时间段+分页
     * @param watermeterRecordManagerVO
     * @return
     */
    @Override
    public List<WatermeterRecordManagerVO> findWatermeterRecordByWatermeterIdAndTime(WatermeterRecordManagerVO watermeterRecordManagerVO) {
        Log.info("查询水表查表记录by时间段，watermeterRecordManagerVO:{}",watermeterRecordManagerVO.toString());
        if(watermeterRecordManagerVO.getPage()!= null && watermeterRecordManagerVO.getRows() != null){
            PageHelper.startPage(watermeterRecordManagerVO.getPage(),watermeterRecordManagerVO.getRows());
        }
        List<WatermeterRecordManagerVO> watermeterRecordManagerVOList=watermeterManagerMapper.selectWatermeterRecordByWatermeterIdAndTime(watermeterRecordManagerVO);

        if(!watermeterRecordManagerVOList.isEmpty() || watermeterRecordManagerVOList != null) {
            for (int i = 0; i < watermeterRecordManagerVOList.size(); i++) {
                int meterAmount = 0;
                if(i==watermeterRecordManagerVOList.size()-1){
                    meterAmount=0;
                }else {
                    meterAmount = watermeterRecordManagerVOList.get(i+1).getDeviceAmount();
                }
                int dayAmount=watermeterRecordManagerVOList.get(i).getDeviceAmount()-meterAmount;
                watermeterRecordManagerVOList.get(i).setDayAmount(dayAmount);
                watermeterRecordManagerVOList.get(i).setRows(watermeterRecordManagerVO.getRows());
            }
        }

        return watermeterRecordManagerVOList;
    }

    /**
     * 水表异常记录
     * @param exceptionVO
     *  @return
     */
    @Override
    public List<ExceptionWebVO> findWatermeterException(ExceptionWebVO exceptionVO) {
        Log.info("水表异常记录，exceptionVO:{}",exceptionVO.toString());
        if(exceptionVO.getPage()!= null && exceptionVO.getRows() != null){
            PageHelper.startPage(exceptionVO.getPage(),exceptionVO.getRows());
        }
        List<ExceptionWebVO> exceptionVOList = watermeterManagerMapper.findwebWatermeterExceptionByWatermeterId(exceptionVO.getDaviceId());

        return exceptionVOList;
    }

    /**
     * 查询网关列表
     * @param gatewayWebListVo
     * @return
     */
    @Override
    public List<GatewayWebListVo> gatewayWebListVoList(GatewayWebListVo gatewayWebListVo) {
        Log.info("查询网关列表，gatewayWebListVo:{}",gatewayWebListVo.toString());
        if(gatewayWebListVo.getPage()!= null && gatewayWebListVo.getRows() != null){
            PageHelper.startPage(gatewayWebListVo.getPage(),gatewayWebListVo.getRows());
        }
        //分散式
        if(gatewayWebListVo.getType().equals(HouseStyleEnum.DISPERSED.getCode())){
            return watermeterManagerMapper.findHmGatewayWebListVoList(gatewayWebListVo);
        } else if(gatewayWebListVo.getType().equals(HouseStyleEnum.CONCENTRAT.getCode())){
            //集中式
            return watermeterManagerMapper.findJzGatewayWebListVoList(gatewayWebListVo);
        }else{
            return null;
        }
    }

    /**
     * 查询网关详情
     * @param smartGatewayId
     * @param type
     * @return
     */
    @Override
    public GatewayWebDetailVO findGatewayDetailbyId (int smartGatewayId, String type) {
        Log.info("查询网关详情，smartGatewayId:{},type:{}",smartGatewayId,type);
        //分散式
        if(type.equals(HouseStyleEnum.DISPERSED.getCode())){
            GatewayWebDetailVO gatewayWebDetailVO = watermeterManagerMapper.selectHmGatewayDetailbyGatewayId(smartGatewayId);
            List<GatewayWatermeterWebListVO> watermeterList=watermeterManagerMapper.selectHmGatewayWatermeterListByGatewayId(smartGatewayId);
            gatewayWebDetailVO.setWatermeterList(watermeterList);
            return gatewayWebDetailVO;
        } else if(type.equals(HouseStyleEnum.CONCENTRAT.getCode())){
            //集中式
            GatewayWebDetailVO gatewayWebDetailVO =watermeterManagerMapper.selectJzGatewayDetailbyGatewayId(smartGatewayId);
            List<GatewayWatermeterWebListVO> watermeterList=watermeterManagerMapper.selectJzGatewayWatermeterListByGatewayId(smartGatewayId);
            gatewayWebDetailVO.setWatermeterList(watermeterList);
            return gatewayWebDetailVO;
        }else{
            return null;
        }
    }

    /**
     * 查询网关异常记录
     * @param exceptionVO
     * @return
     */
    @Override
    public List<ExceptionWebVO> findGatewayException(ExceptionWebVO exceptionVO) {
        Log.info("查询网关异常记录，exceptionVO:{}",exceptionVO.toString());
        if(exceptionVO.getPage()!= null && exceptionVO.getRows() != null){
            PageHelper.startPage(exceptionVO.getPage(),exceptionVO.getRows());
        }
        List<ExceptionWebVO> exceptionVOList = watermeterManagerMapper.findwebWatermeterGatewayExceptionByGatewayId(exceptionVO.getDaviceId());

        return exceptionVOList;
    }

    /**
     * 查询水表查表记录by时间段+分页
     * @param watermeterRecordManagerVO
     * @return
     */
    @Override
    public List<WatermeterRecordManagerVO> findWatermeterRecordByWatermeterIdAndTime2(WatermeterRecordManagerVO watermeterRecordManagerVO) {
        Log.info("查询水表查表记录by时间段，watermeterRecordManagerVO:{}",watermeterRecordManagerVO.toString());

        List<WatermeterRecordManagerVO> watermeterRecordManagerVOList=watermeterManagerMapper.selectWatermeterRecordByWatermeterIdAndTime(watermeterRecordManagerVO);

        if(!watermeterRecordManagerVOList.isEmpty() || watermeterRecordManagerVOList != null) {
            for (int i = 0; i < watermeterRecordManagerVOList.size(); i++) {
                int meterAmount = 0;
                if(i==watermeterRecordManagerVOList.size()-1){
                    meterAmount=0;
                }else {
                    meterAmount = watermeterRecordManagerVOList.get(i+1).getDeviceAmount();
                }
                int dayAmount=watermeterRecordManagerVOList.get(i).getDeviceAmount()-meterAmount;
                watermeterRecordManagerVOList.get(i).setDayAmount(dayAmount);
                watermeterRecordManagerVOList.get(i).setRows(watermeterRecordManagerVO.getRows());
            }
        }

        return watermeterRecordManagerVOList;
    }

}