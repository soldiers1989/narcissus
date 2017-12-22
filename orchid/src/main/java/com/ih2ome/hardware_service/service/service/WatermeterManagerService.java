package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.vo.WatermeterManagerDetailVO;
import com.ih2ome.hardware_service.service.vo.WatermeterRecordManagerVO;
import com.ih2ome.hardware_service.service.vo.WatermeterWebListVo;

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
}
