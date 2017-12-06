package com.ih2ome.peony.watermeterInterface;

/**
 *
 */
public interface IWatermeter {

    /**
     * 查询房子状态是否已同步
     * @param home_id
     */
    void findHomeState(String home_id);

}
