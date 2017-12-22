package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.common.base.MyMapper;
import com.ih2ome.hardware_service.service.model.volga.Apartment;
import com.ih2ome.hardware_service.service.vo.WatermeterManagerDetailVO;
import com.ih2ome.hardware_service.service.vo.WatermeterRecordManagerVO;
import com.ih2ome.hardware_service.service.vo.WatermeterWebListVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatermeterManagerMapper extends MyMapper<Apartment>{

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

}
