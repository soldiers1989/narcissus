package com.ih2ome.peony.watermeterInterface;

import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.AddHomeVo;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.AddRoomVO;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface IWatermeter {

    /**
     * 查询房子状态是否已同步
     * @param home_id
     */
    String findHomeState(String home_id, String userId) throws WatermeterException;

    /**
     * 查询多个房子状态是否已同步
     * @param home_id
     */
    String findHomeStates(String[] home_id, String userId) throws WatermeterException;

    /**
     * 添加一个房源
     * @param home
     */
    String addHome(AddHomeVo home, String userId) throws WatermeterException;

    /**
     * 水表抄表请求
     * @param uuid
     * @param manufactory
     * @return
     */
    String readWatermeter(String uuid, String manufactory, String userId) throws WatermeterException;

    /**
     * 获取抄表状态
     * @param uuid
     * @param manufactory
     * @return
     */
    String readWatermeterStatus(String uuid, String manufactory, String userId) throws WatermeterException;

    /**
     * 给指定的公寓添加房间
     * @param home_id
     * @param room_id
     * @return
     * @throws WatermeterException
     */
    String addRoom(String home_id, String room_id, String room_name, String rooom_description, String userId) throws WatermeterException;

    /**
     * 给指定的公寓添加房间
     * @param home_id
     * @param rooms
     * @return
     * @throws WatermeterException
     */
    String addRooms(String home_id, List<AddRoomVO> rooms, String userId) throws WatermeterException;

    /**
     * 获取设备历史异常记录
     * @param uuid
     * @param offset
     * @param count
     * @param start_time
     * @param end_time
     * @return
     * @throws WatermeterException
     */
    String deviceFetchExceptions(String uuid, int offset, int count, int start_time, int end_time, String userId) throws WatermeterException;

    /**
     * 获取水表网关信息
     * @param uuid
     * @return
     * @throws WatermeterException
     */
    String getWaterGatewayInfo(String uuid ,String userId) throws WatermeterException;

    /**
     * 获取水表信息
     * @param uuid
     * @return
     * @throws WatermeterException
     */
    String getWatermeterInfo(String uuid, String manufactory,String userId) throws WatermeterException;

    /**
     * 获取抄表历史
     * @param uuid
     * @return
     * @throws WatermeterException
     */
    String getMeterRecord(String uuid, String manufactory, String room_id, int type, int count, int offset, int begin, int end, String userId) throws WatermeterException;

    /**
     * 云丁doGet请求
     * @param uri
     * @param map
     * @return
     * @throws WatermeterException
     */
    String yunDingDoGetUrl(String uri, Map<String, Object> map, String userId) throws WatermeterException;

    /**
     * 云丁doGet请求
     * @param uri
     * @param map
     * @return
     * @throws WatermeterException
     */
    String yunDingDoPostUrl(String uri, Map<String, Object> map, String userId) throws WatermeterException;
}
