package com.ih2ome.peony.watermeterInterface;

import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.peony.watermeterInterface.vo.AddHomeVo;

/**
 *
 */
public interface IWatermeter {

    /**
     * 查询房子状态是否已同步
     * @param home_id
     */
    String findHomeState(String home_id) throws WatermeterException;

    /**
     * 添加一个房源
     * @param home
     */
    void addHome(AddHomeVo home) throws WatermeterException;

}
