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
     * 查询多个房子状态是否已同步
     * @param home_id
     */
    String findHomeStates(String[] home_id) throws WatermeterException;

    /**
     * 添加一个房源
     * @param home
     */
    String addHome(AddHomeVo home) throws WatermeterException;

    /**
     * 读取实时抄表记录
     * @param uuid
     * @param manufactory
     * @return
     */
    String readWatermeter(String uuid,String manufactory) throws WatermeterException;

    /**
     * 给指定的公寓添加房间
     * @param home_id
     * @param room_id
     * @return
     * @throws WatermeterException
     */
    String addRoom(String home_id,String room_id,String room_name,String rooom_description) throws WatermeterException;

    /**
     * 给指定的公寓添加房间
     * @param home_id
     * @param rooms
     * @return
     * @throws WatermeterException
     */
    String addRooms(String home_id,String[] rooms) throws WatermeterException;



}
