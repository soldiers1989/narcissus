package com.ih2ome.hardware_service.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.hardware_service.service.dao.SynchronousHomeMapper;
import com.ih2ome.hardware_service.service.enums.HomeSyncEnum;
import com.ih2ome.hardware_service.service.enums.HouseCatalogEnum;
import com.ih2ome.hardware_service.service.service.SynchronousHomeService;
import com.ih2ome.hardware_service.service.vo.AddRoomVO;
import com.ih2ome.hardware_service.service.vo.ApartmentVO;
import com.ih2ome.hardware_service.service.vo.JZWatermeterDetailVO;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.peony.watermeterInterface.enums.WATERMETER_FIRM;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.peony.watermeterInterface.vo.AddHomeVo;
import com.ih2ome.peony.watermeterInterface.vo.YunDingResponseVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SynchronousHomeServiceImpl implements SynchronousHomeService{

    @Resource
    private SynchronousHomeMapper synchronousHomeMapper;

    /**
     * 集中式同步房源by楼层
     * @param apartmentId
     * @param floorId
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws WatermeterException
     */
    @Override
    public String synchronousHousingByFloorId(int apartmentId, int floorId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException {
        IWatermeter iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();

        //房源home是否已同步
        String state = iWatermeter.findHomeState(String.valueOf(apartmentId));
        String res =null;
        if (state == null) {
            //查询home信息
            AddHomeVo addHomeVo = synchronousHomeMapper.findHouseByApartmentId(floorId);
            addHomeVo.setHome_type(HouseCatalogEnum.HOUSE_CATALOG_ENUM_VOLGA.getCode());
            addHomeVo.setCountry("中国");
            //添加房源
            res = iWatermeter.addHome(addHomeVo);
        }

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            throw new WatermeterException("json格式解析错误"+e.getMessage());
        }

        String home_id = resJson.get("home_id").toString();
        if(!home_id.equals(String.valueOf(apartmentId))){
            return null;
        }
        //更新房源为已同步至云丁
        synchronousHomeMapper.updateHomeSyncByApartmentId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(),apartmentId);

        //添加room
        //查询所有roombyFloorId
        List<AddRoomVO> addRoomVOS=synchronousHomeMapper.findRoomByFloorId(floorId);
        List<AddRoomVO> addRoomVOSList =new ArrayList<>();
        for (AddRoomVO addRoomVO:addRoomVOS
                ) {
            addRoomVO.setRoom_id("jz" + addRoomVO.getRoom_id());
            addRoomVOSList.add(addRoomVO);
        }

        String[] strings = new String[addRoomVOSList.size()];
        strings=addRoomVOSList.toArray(strings);

        String addRoomsRes = iWatermeter.addRooms(String.valueOf(apartmentId),strings);

        JSONObject resJson2 = JSONObject.parseObject(addRoomsRes);
        String room_id = String.valueOf(resJson2.get("room_id"));
        //room_id不为空添加成功
        if (room_id != null){
            //更新room为已同步
            //synchronousHomeMapper.updataRoomSyncByRoomId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(),addRoomVOS);
        }
        return resJson.get("home_id").toString();
    }

    /**
     * 集中式同步房源
     * @param apartmentId
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws WatermeterException
     */
    @Override
    public String synchronousHousingByApartmenId(int apartmentId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException{
        IWatermeter iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();

        //查询home信息
        AddHomeVo addHomeVo = synchronousHomeMapper.findHouseByApartmentId(apartmentId);
        addHomeVo.setHome_type(2);
        addHomeVo.setCountry("中国");
        addHomeVo.setHome_id("jz"+addHomeVo.getHome_id());
        //添加房源
        String res = iWatermeter.addHome(addHomeVo);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            throw new WatermeterException("json格式解析错误"+e.getMessage());
        }

        String home_id = resJson.get("home_id").toString();
        if(!home_id.equals(String.valueOf(apartmentId))){
            return null;
        }
        //添加room
        //查询所有roombyApartmentId
        List<AddRoomVO> addRoomVOS=synchronousHomeMapper.findRoomByApartmentId(apartmentId);
        List<AddRoomVO> addRoomVOSList =new ArrayList<>();
        for (AddRoomVO addRoomVO:addRoomVOS
                ) {
            addRoomVO.setRoom_id("jz" + addRoomVO.getRoom_id());
            addRoomVOSList.add(addRoomVO);
        }

        String[] strings = new String[addRoomVOSList.size()];
        strings=addRoomVOSList.toArray(strings);

        String addRoomsRes = iWatermeter.addRooms(String.valueOf(apartmentId),strings);

        return resJson.get("home_id").toString();
    }

    /**
     * 查询floorIdbyRoomid
     * @param room_id
     * @return
     */
    @Override
    public Long findFloorIdByRoomId(Long room_id) {
        return  synchronousHomeMapper.findFloorIdByRoomId(room_id);
    }

    /**
     * 查询公寓信息
     * @param id
     * @return
     */

    @Override
    public List<ApartmentVO> findApartmentIdByUserId(int id) {
        //查询公寓信息
        List<ApartmentVO> apartmentVOS=synchronousHomeMapper.findApartmentByUserId(id);
        return apartmentVOS;
    }

    /**
     * 查询集中式水表列表通过楼层id
     * @param floorId
     * @return
     */
    @Override
    public List<JZWatermeterDetailVO> findWatermetersByFloorId(int floorId) {
        //通过楼层ids查询水表
        List<JZWatermeterDetailVO> jzWatermeterDetailVOS = synchronousHomeMapper.findWatermetersByFloorId(floorId);
        return jzWatermeterDetailVOS;

    }

    /**
     * 查询房源是否已同步byhomeIds
     * @param homeIds
     * @return
     */
    @Override
    public List<YunDingResponseVo> findHomeIsSynchronousedByHomeIds(String[] homeIds) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException  {
        IWatermeter iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();

        String res= iWatermeter.findHomeStates(homeIds);
        if (res == null){
            return null;
        }
        List<YunDingResponseVo> jsonObject=JSONObject.parseArray(res,YunDingResponseVo.class);
        return jsonObject;
    }

}
