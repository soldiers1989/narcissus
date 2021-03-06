package com.ih2ome.hardware_service.service.service;

import com.ih2ome.sunflower.vo.pageVo.watermeter.*;

import java.util.List;

public interface WatermeterManagerService {
    /**
     * 水表list
     * @param watermeterWebListVo
     * @return
     */
    List<WatermeterWebListVo> watermeterWebListVoList(WatermeterWebListVo watermeterWebListVo);

    /**
     * 水表详情
     *
     * @param uuid
     * @param type
     * @return
     */
    WatermeterManagerDetailVO findWatermeterDetailByUuid(String uuid, String type);

    /**
     * 水表抄表记录根据时间段查询
     * @param watermeterRecordManagerVO
     * @return
     */
    List<WatermeterRecordManagerVO> findWatermeterRecordByWatermeterIdAndTime(WatermeterRecordManagerVO watermeterRecordManagerVO);

    /**
     * 查找水表异常记录
     * @param exceptionVO
     * @return
     */
    List<ExceptionWebVO> findWatermeterException(ExceptionWebVO exceptionVO);

    /**
     * 网关list
     * @param gatewayWebListVo
     * @return
     */
    List<GatewayWebListVo> gatewayWebListVoList(GatewayWebListVo gatewayWebListVo);

    /**
     * 网关详情
     * @param smartGatewayId
     * @param type
     * @return
     */
    GatewayWebDetailVO findGatewayDetailbyId(int smartGatewayId, String type);

    /**
     * 网关异常记录
     * @param exceptionVO
     * @return
     */
    List<ExceptionWebVO> findGatewayException(ExceptionWebVO exceptionVO);

    /**
     * 查询水表抄表记录by时间段（无分页）
     * @param watermeterRecordManagerVO
     * @return
     */
    List<WatermeterRecordManagerVO> findWatermeterRecordByWatermeterIdAndTime2(WatermeterRecordManagerVO watermeterRecordManagerVO);
}
