package com.ih2ome.hardware_service.service.dao;


import com.ih2ome.common.base.MyMapper;
import com.ih2ome.sunflower.entity.volga.Apartment;
import com.ih2ome.hardware_service.service.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatermeterManagerMapper extends MyMapper<Apartment> {

    /**
     * 查找集中式水表list
     * @param watermeterWebListVo
     * @return
     */
    List<WatermeterWebListVo> findJzWatermeterWebListVoList(WatermeterWebListVo watermeterWebListVo);

    /**
     * 查找分散式水表list
     * @param watermeterWebListVo
     * @return
     */
    List<WatermeterWebListVo> findHmWatermeterWebListVoList(WatermeterWebListVo watermeterWebListVo);

    /**
     * 查询分散式水表详情
     * @param uuid
     * @return
     */
    WatermeterManagerDetailVO selectHmWatermeterDetailByUuid(String uuid);

    /**
     * 查询集中式水表详情
     * @param uuid
     * @return
     */
    WatermeterManagerDetailVO selectJzWatermeterDetailByUuid(String uuid);

    /**
     * 查询水表抄表记录by时间段+分页
     * @param watermeterRecordManagerVO
     * @return
     */
    List<WatermeterRecordManagerVO> selectWatermeterRecordByWatermeterIdAndTime(WatermeterRecordManagerVO watermeterRecordManagerVO);


    /**
     * 查找分散式网关list
     * @param gatewayWebListVo
     * @return
     */
    List<GatewayWebListVo> findHmGatewayWebListVoList(GatewayWebListVo gatewayWebListVo);

    /**
     * 查找集中式网关list
     * @param gatewayWebListVo
     * @return
     */
    List<GatewayWebListVo> findJzGatewayWebListVoList(GatewayWebListVo gatewayWebListVo);

    /**
     * 查询分散式网关详情
     * @param smartGatewayId
     * @return
     */
    GatewayWebDetailVO selectHmGatewayDetailbyGatewayId(int smartGatewayId);

    /**
     * 查询集中式网关详情
     * @param smartGatewayId
     * @return
     */
    GatewayWebDetailVO selectJzGatewayDetailbyGatewayId(int smartGatewayId);

    /**
     * 查询分散式网关详情下的水表list
     * @param smartGatewayId
     * @return
     */
    List<GatewayWatermeterWebListVO> selectHmGatewayWatermeterListByGatewayId(int smartGatewayId);

    /**
     * 查询分散式网关详情下的水表list
     * @param smartGatewayId
     * @return
     */
    List<GatewayWatermeterWebListVO> selectJzGatewayWatermeterListByGatewayId(int smartGatewayId);

    /**
     * 查询分散式房源同步状态
     * @param synchronousHomeWebVo
     * @return
     */
    List<SynchronousHomeWebVo> selectHmHomeSynchronousStatus(SynchronousHomeWebVo synchronousHomeWebVo);

    /**
     * 查询集中式房源同步状态
     * @param synchronousHomeWebVo
     * @return
     */
    List<SynchronousHomeWebVo> selectJzHomeSynchronousStatus(SynchronousHomeWebVo synchronousHomeWebVo);

    /**
     * 查询分散式房源同步状态
     * @param homeId
     * @param synchronous
     * @return
     */
    List<HmRoomSyncVO> selectHmRoomSynchronousStatus(@Param("homeId") int homeId,@Param("synchronous") int synchronous);

    /**
     * 查询集中式房源同步状态
     * @param homeId
     * @param synchronous
     * @return
     */
    List<HmRoomSyncVO> selectJzRoomSynchronousStatus(@Param("homeId") int homeId,@Param("synchronous") int synchronous);

    /**
     * 查询分散式room是否全同步
     * @param homeId
     * @param synchronous
     * @return
     */
    List<HmRoomSyncVO> selectHmRoomIsAllSynchronous(@Param("homeId") int homeId,@Param("synchronous") int synchronous);

    /**
     * 查询集中式room是否全同步
     * @param homeId
     * @param synchronous
     * @return
     */
    List<HmRoomSyncVO> selectJzRoomIsAllSynchronous(@Param("homeId") int homeId,@Param("synchronous") int synchronous);

    /**
     * 查询网关异常记录
     * @param daviceId
     * @return
     */
    List<ExceptionWebVO> findwebWatermeterGatewayExceptionByGatewayId(int daviceId);

    /**
     * 查询水表异常记录
     * @param daviceId
     * @return
     */
    List<ExceptionWebVO> findwebWatermeterExceptionByWatermeterId(int daviceId);
}
