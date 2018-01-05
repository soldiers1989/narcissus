package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.vo.*;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;

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
    List<ExceptionVO> findWatermeterException(ExceptionVO exceptionVO);

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
    List<ExceptionVO> findGatewayException(ExceptionVO exceptionVO);

    /**
     * 房源同步状态
     * @param synchronousHomeWebVo
     * @return
     */
    List<SynchronousHomeWebVo> findHomeSynchronousStatus(SynchronousHomeWebVo synchronousHomeWebVo);

    /**
     * 房间同步状态
     * @param homeId
     * @param syncStatus
     *@param type  @return
     */
    List<HmRoomSyncVO> findRoomSynchronousStatus(int homeId, int syncStatus, String type);

    /**
     * 同步房源
     * @param homeAndRoomSyncVO
     * @param type
     * @return
     */
    HomeAndRoomSyncVO synchronousHomeAndRoom(HomeAndRoomSyncVO homeAndRoomSyncVO, String type) throws ClassNotFoundException, WatermeterException, InstantiationException, IllegalAccessException;
}