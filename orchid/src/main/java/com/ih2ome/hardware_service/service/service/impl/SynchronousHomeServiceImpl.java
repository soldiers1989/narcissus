package com.ih2ome.hardware_service.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.hardware_service.service.dao.SynchronousHomeMapper;
import com.ih2ome.hardware_service.service.enums.HomeIdNameEnum;
import com.ih2ome.hardware_service.service.enums.HomeSyncEnum;
import com.ih2ome.hardware_service.service.enums.HouseCatalogEnum;
import com.ih2ome.hardware_service.service.service.SynchronousHomeService;
import com.ih2ome.hardware_service.service.vo.*;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.peony.watermeterInterface.enums.WATERMETER_FIRM;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.peony.watermeterInterface.vo.AddHomeVo;
import com.ih2ome.peony.watermeterInterface.vo.AddRoomVO;
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
        JSONObject jsonObject = JSONObject.parseObject(state);
        Object result = jsonObject.get("result");
        String res =null;
        if (result == null) {
            //查询home信息
            AddHomeVo addHomeVo = synchronousHomeMapper.findHouseByApartmentId(floorId);
            addHomeVo.setHome_type(HouseCatalogEnum.HOUSE_CATALOG_ENUM_VOLGA.getCode());
            addHomeVo.setCountry("中国");
            //jz区分集中式和分散式的homeId
            addHomeVo.setHome_id(HomeIdNameEnum.HOME_ID_NAME_JZ.getCode()+addHomeVo.getHome_id());
            //添加房源
            res = iWatermeter.addHome(addHomeVo);

            JSONObject resJson = JSONObject.parseObject(res);

            String home_id = resJson.get("home_id").toString();
            if(!home_id.equals(HomeIdNameEnum.HOME_ID_NAME_JZ.getCode()+apartmentId)){
                return null;
            }
            //更新房源为已同步至云丁
            synchronousHomeMapper.updateHomeSyncByApartmentId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(),apartmentId);
        }


        //添加room
        //查询所有roombyFloorId
        List<AddRoomVO> addRoomVOS=synchronousHomeMapper.findRoomByFloorId(floorId);
        List<AddRoomVO> addRoomVOSList =new ArrayList<>();
        for (AddRoomVO addRoomVO:addRoomVOS
                ) {
            addRoomVO.setRoom_id(HomeIdNameEnum.HOME_ID_NAME_JZ.getCode() + addRoomVO.getRoom_id());
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
            synchronousHomeMapper.updataRoomSyncByRoomId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(),addRoomVOS);
            //更新floor为已同步
            synchronousHomeMapper.updataFloorSyncByFloorId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(),floorId);
        }
        return "success";
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
        addHomeVo.setHome_type(HouseCatalogEnum.HOUSE_CATALOG_ENUM_VOLGA.getCode());
        addHomeVo.setCountry("中国");
        //jz区分集中式和分散式的homeId
        addHomeVo.setHome_id(HomeIdNameEnum.HOME_ID_NAME_JZ.getCode()+addHomeVo.getHome_id());
        //添加房源
        String res = iWatermeter.addHome(addHomeVo);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            throw new WatermeterException("json格式解析错误"+e.getMessage());
        }

        String home_id = resJson.get("home_id").toString();
        if(!home_id.equals(HomeIdNameEnum.HOME_ID_NAME_JZ.getCode()+apartmentId)){
            return null;
        }
        //更新房源为已同步至云丁
        synchronousHomeMapper.updateHomeSyncByApartmentId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(),apartmentId);

        //添加room
        //查询所有roombyApartmentId
        List<AddRoomVO> addRoomVOS=synchronousHomeMapper.findRoomByApartmentId(apartmentId);
        List<AddRoomVO> addRoomVOSList =new ArrayList<>();
        for (AddRoomVO addRoomVO:addRoomVOS
                ) {
            //jz区分集中式和分散式的homeId
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
            synchronousHomeMapper.updataRoomSyncByRoomId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(),addRoomVOS);
            //更新floor为已同步
            //查询floorIds
            List<Integer> floorIds=synchronousHomeMapper.findFloorIdsByApartmentId(apartmentId);
            synchronousHomeMapper.updataFloorSyncByFloorIds(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(),floorIds);
        }

        return "success";
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
     * 查询集中式房源是否已同步byhomeIds
     * @param userid
     * @return
     */
    @Override
    public List<HomeSyncVO> findHomeIsSynchronousedByUserId(int userid) {
        return synchronousHomeMapper.findApartmentIsSynchronousedByUserId(userid);
    }

    /**
     * 查询分散式房源是否已同步byhomeIds
     * @param userid
     * @return
     */
    @Override
    public List<HomeSyncVO> findHmHomeIsSynchronousedByUserId(int userid) {
        return synchronousHomeMapper.findHouseIsSynchronousedByUserId(userid);
    }

    /**
     * 查询房源是否同步by房源id(第三方查询)
     * @param homeId
     * @param type
     * @return
     */
    @Override
    public YunDingResponseVo findHomeIsSynchronousedByHomeId(int homeId, int type) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException {
        IWatermeter iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();
        String devId = null;
        String home=null;
        if(type == HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode()){
            home="hm"+homeId;
        }else {
            home="jz"+homeId;
        }
        String res= iWatermeter.findHomeState(home);
        if (res == null){
            return null;
        }
        YunDingResponseVo jsonObject=JSONObject.parseObject(res,YunDingResponseVo.class);
        return jsonObject;
    }

    /**
     * 分散式用户房源
     * @param id
     * @return
     */
    @Override
    public List<HouseVO> findHouseByUserId(int id) {
        return synchronousHomeMapper.findHouseByUserId(id);
    }

    /**
     * 分散式同步房源
     * @param houseId
     * @return
     */
    @Override
    public String synchronousHousingByHouseId(int houseId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException{
        IWatermeter iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();

        //查询house信息
        AddHomeVo addHomeVo = synchronousHomeMapper.findHouseByHouseId(houseId);

        addHomeVo.setHome_id(HomeIdNameEnum.HOME_ID_NAME_HM.getCode()+addHomeVo.getHome_id());
        addHomeVo.setHome_type(HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode());
        addHomeVo.setCountry("中国");
        //添加房源
        String res = iWatermeter.addHome(addHomeVo);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            throw new WatermeterException("json格式解析错误"+e.getMessage());
        }

        String home_id = resJson.get("home_id").toString();
        if(!home_id.equals(HomeIdNameEnum.HOME_ID_NAME_HM.getCode()+String.valueOf(houseId))){
            return null;
        }
        //更新房源为已同步至云丁
        synchronousHomeMapper.updateHouseSyncByHouseId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(),houseId);

        //添加room
        //查询所有roombyhouseid
        List<AddRoomVO> addRoomVOS=synchronousHomeMapper.findRoomByHouseId(houseId);
        List<AddRoomVO> addRoomVOSList =new ArrayList<>();
        for (AddRoomVO addRoomVO:addRoomVOS
                ) {
            addRoomVO.setRoom_id(HomeIdNameEnum.HOME_ID_NAME_HM.getCode() + addRoomVO.getRoom_id());
            addRoomVOSList.add(addRoomVO);
        }

        String[] strings = new String[addRoomVOSList.size()];
        strings=addRoomVOSList.toArray(strings);

        String addRoomsRes = iWatermeter.addRooms("hm"+houseId,strings);

        JSONObject resJson2 = JSONObject.parseObject(addRoomsRes);
        String room_id = String.valueOf(resJson2.get("room_id"));
        //room_id不为空添加成功
        if (room_id != null){
            //更新room为已同步
            synchronousHomeMapper.updataHmRoomSyncByRoomId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(),addRoomVOS);
        }
        return "success";
    }

    /**
     * 集中式公寓查询by公寓id
     * @param apartmentId
     * @return
     */
    @Override
    public ApartmentVO findApartmentIdByApartmentId(int apartmentId) {
        return synchronousHomeMapper.selectApartmentIdByApartmentId(apartmentId);
    }


}
