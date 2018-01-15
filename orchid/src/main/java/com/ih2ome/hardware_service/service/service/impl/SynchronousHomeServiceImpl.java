package com.ih2ome.hardware_service.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.hardware_service.service.dao.SynchronousHomeMapper;
import com.ih2ome.hardware_service.service.enums.HomeIdNameEnum;
import com.ih2ome.hardware_service.service.enums.HomeSyncEnum;
import com.ih2ome.hardware_service.service.enums.HouseCatalogEnum;
import com.ih2ome.hardware_service.service.service.SynchronousHomeService;
import com.ih2ome.hardware_service.service.vo.ApartmentVO;
import com.ih2ome.hardware_service.service.vo.HomeSyncVO;
import com.ih2ome.hardware_service.service.vo.HouseVO;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.peony.watermeterInterface.enums.WATERMETER_FIRM;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.peony.watermeterInterface.vo.AddHomeVo;
import com.ih2ome.peony.watermeterInterface.vo.AddRoomVO;
import com.ih2ome.peony.watermeterInterface.vo.YunDingResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SynchronousHomeServiceImpl implements SynchronousHomeService{

    @Resource
    private SynchronousHomeMapper synchronousHomeMapper;

    private static final Logger Log = LoggerFactory.getLogger(SynchronousHomeServiceImpl.class);

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
        Log.info("集中式同步房源，公寓id：{}",apartmentId);
        IWatermeter iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();
        String homeId=HomeIdNameEnum.HOME_ID_NAME_JZ.getCode()+apartmentId;
        //查询home信息
        AddHomeVo addHomeVo = synchronousHomeMapper.findHouseByApartmentId(apartmentId);
        if (addHomeVo==null){
            return "房源不存在";
        }
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

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("添加房源失败，"+msg);
            return "添加房源失败，"+msg;
        }

        //更新房源为已同步至云丁
        synchronousHomeMapper.updateHomeSyncByApartmentId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(),apartmentId);

        //添加room
        //查询所有roombyApartmentId
        List<AddRoomVO> addRoomVOS=synchronousHomeMapper.findRoomByApartmentId(apartmentId);
        if (addRoomVOS.isEmpty() || addRoomVOS==null){
            return "没有查到公寓下的房间";
        }
        List<AddRoomVO> addRoomVOSList =new ArrayList<>();
        for (AddRoomVO addRoomVO:addRoomVOS
                ) {
            //jz区分集中式和分散式的homeId
            addRoomVO.setRoom_id(HomeIdNameEnum.HOME_ID_NAME_JZ.getCode() + addRoomVO.getRoom_id());
            addRoomVOSList.add(addRoomVO);
        }

        String addRoomsRes = iWatermeter.addRooms(homeId,addRoomVOSList);

        JSONObject resJson2 = JSONObject.parseObject(addRoomsRes);
        String errNo = resJson2.get("ErrNo").toString();
        //添加成功
        if (errNo.equals("0") ){
            List<Integer> roomIds=new ArrayList<>();
            for (AddRoomVO addRoomVO:addRoomVOS) {
                roomIds.add(Integer.parseInt(addRoomVO.getRoom_id().substring(2)));
            }
            //更新room为已同步
            synchronousHomeMapper.updataRoomSyncByRoomId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(),roomIds);
            //更新floor为已同步
            //查询floorIds
            List<Integer> floorIds=synchronousHomeMapper.findFloorIdsByApartmentId(apartmentId);
            synchronousHomeMapper.updataFloorSyncByFloorIds(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(),floorIds);
        }

        return "success";
    }


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
        Log.info("集中式同步房源by楼层,公寓apartmentId：{},楼层floorId：{}",apartmentId,floorId);
        IWatermeter iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();
        String homeId=HomeIdNameEnum.HOME_ID_NAME_JZ.getCode()+apartmentId;
        //房源home是否已同步
        String state = iWatermeter.findHomeState(homeId);
        JSONObject jsonObject=null;
        try {
            jsonObject = JSONObject.parseObject(state);
        }catch (Exception e){
            Log.error("json格式解析错误",e);
            throw new WatermeterException("json格式解析错误"+e.getMessage());
        }

        Object result = jsonObject.get("result");
        String res =null;
        if (result == null) {
            //查询home信息
            AddHomeVo addHomeVo = synchronousHomeMapper.findHouseByApartmentId(apartmentId);
            addHomeVo.setHome_type(HouseCatalogEnum.HOUSE_CATALOG_ENUM_VOLGA.getCode());
            addHomeVo.setCountry("中国");
            //jz区分集中式和分散式的homeId
            addHomeVo.setHome_id(HomeIdNameEnum.HOME_ID_NAME_JZ.getCode()+addHomeVo.getHome_id());
            //添加房源
            res = iWatermeter.addHome(addHomeVo);

            JSONObject resJson = null;
            try {
                resJson = JSONObject.parseObject(res);
            }catch (Exception e){
                Log.error("json格式解析错误",e);
                throw new WatermeterException("json格式解析错误"+e.getMessage());
            }

            String code = resJson.get("ErrNo").toString();
            if(!code.equals("0")){
                String msg = resJson.get("ErrMsg").toString();
                Log.error("添加房源失败，"+msg);
                throw new WatermeterException("添加房源失败，"+msg);
            }
            //更新房源为已同步至云丁
            synchronousHomeMapper.updateHomeSyncByApartmentId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(),apartmentId);
        }


        //添加room
        //查询所有roombyFloorId
        List<AddRoomVO> addRoomVOS=synchronousHomeMapper.findRoomByFloorId(floorId);
        //list非空判断
        if(addRoomVOS==null || addRoomVOS.isEmpty()) {
            return "房源下没有找到房间";
        }

            List<AddRoomVO> addRoomVOSList =new ArrayList<>();
            for (AddRoomVO addRoomVO : addRoomVOS ) {
                addRoomVO.setRoom_id(HomeIdNameEnum.HOME_ID_NAME_JZ.getCode() + addRoomVO.getRoom_id());
                addRoomVOSList.add(addRoomVO);
            }

            String addRoomsRes = iWatermeter.addRooms(homeId, addRoomVOSList);
            JSONObject resJson = null;
            try {
                resJson = JSONObject.parseObject(addRoomsRes);
            }catch (Exception e){
                Log.error("json格式解析错误",e);
                throw new WatermeterException("json格式解析错误"+e.getMessage());
            }

            String code = resJson.get("ErrNo").toString();
            if(!code.equals("0")){
                String msg = resJson.get("ErrMsg").toString();
                Log.error("添加room失败，"+msg);
                return floorId+"：添加room失败，"+msg;
            }
            List<Integer> roomIds=new ArrayList<>();
            for (AddRoomVO addRoomVO:addRoomVOS) {
                roomIds.add(Integer.parseInt(addRoomVO.getRoom_id().substring(2)));
            }

                //更新room为已同步
                synchronousHomeMapper.updataRoomSyncByRoomId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(), roomIds);
                //更新floor为已同步
                synchronousHomeMapper.updataFloorSyncByFloorId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(), floorId);

        return String.valueOf(floorId);
    }


    /**
     * 查询floorIdbyRoomid
     * @param room_id
     * @return
     */
    @Override
    public Integer findFloorIdByRoomId(Long room_id) {
        Log.info("查询楼层id,房间roomId：{}",room_id);
        return synchronousHomeMapper.findFloorIdByRoomId(room_id);
    }

    /**
     * 查询公寓信息
     * @param id
     * @return
     */

    @Override
    public List<ApartmentVO> findApartmentIdByUserId(int id) {
        Log.info("查询公寓信息,用户id：{}",id);
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
        Log.info("查询集中式房源是否已同步,用户userid：{}",userid);
        //查询集中式room已全部同步的房源
        List<HomeSyncVO> homeSyncVOList=synchronousHomeMapper.findApartmentAllSynchronousedByUserId(userid,HomeSyncEnum.HOME_SYNC_YUNDING.getCode());
        //查询集中式room未全部同步的房源
        List<HomeSyncVO> homeSyncVOList1=synchronousHomeMapper.findApartmentAllSynchronousedByUserId(userid,HomeSyncEnum.HOME_SYNC_UNSYNC.getCode());
        return addList(homeSyncVOList,homeSyncVOList1);
    }

    /**
     * Z
     * @param userid
     * @return
     */
    @Override
    public List<HomeSyncVO> findHmHomeIsSynchronousedByUserId(int userid) {
        Log.info("查询分散式房源是否已同步,用户id：{}",userid);
        //查询分散式房源room已全部同步的房源
        List<HomeSyncVO> homeSyncVOList=synchronousHomeMapper.findHouseIsSynchronousedByUserId(userid,HomeSyncEnum.HOME_SYNC_YUNDING.getCode());
        //查询分散式房源room未全部同步的房源
        List<HomeSyncVO> homeSyncVOList1=synchronousHomeMapper.findHouseIsSynchronousedByUserId(userid,HomeSyncEnum.HOME_SYNC_UNSYNC.getCode());
        return addList(homeSyncVOList,homeSyncVOList1);
    }

    /**
     * 查询房源是否同步by房源id(第三方查询)
     * @param homeId
     * @param type
     * @return
     */
    @Override
    public YunDingResponseVo findHomeIsSynchronousedByHomeId(int homeId, int type) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException {
        Log.info("查询房源是否同步(第三方查询),房源homeId：{},房源类型type：{}",homeId,type);
        IWatermeter iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();
        String devId = null;
        String home=null;
        if(type == HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode()){
            home=HomeIdNameEnum.HOME_ID_NAME_HM.getCode()+homeId;
        }else {
            home=HomeIdNameEnum.HOME_ID_NAME_JZ.getCode()+homeId;
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
        Log.info("分散式用户房源,用户id：{}",id);
        return synchronousHomeMapper.findHouseByUserId(id);
    }

    /**
     * 分散式同步房源
     * @param houseId
     * @return
     */
    @Override
    public String synchronousHousingByHouseId(int houseId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException{
        Log.info("分散式同步房源,房源houseId：{}",houseId);
        IWatermeter iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();
        //同步房源home
        String homeReslut = synchronousHome(houseId, HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode(), iWatermeter);
        //如果房源同步失败
        if(!homeReslut.equals("success")){
            return homeReslut;
        }
        //添加room
        //查询所有roombyhouseid
        List<AddRoomVO> addRoomVOS=synchronousHomeMapper.findRoomByHouseId(houseId);
        List<AddRoomVO> addRoomVOSList =new ArrayList<>();
        if (addRoomVOS != null) {
            for (AddRoomVO addRoomVO : addRoomVOS
                    ) {
                addRoomVO.setRoom_id(HomeIdNameEnum.HOME_ID_NAME_HM.getCode() + addRoomVO.getRoom_id());
                addRoomVOSList.add(addRoomVO);
            }


            String addRoomsRes = iWatermeter.addRooms(HomeIdNameEnum.HOME_ID_NAME_HM.getCode()+houseId,addRoomVOSList);

            JSONObject resJson = JSONObject.parseObject(addRoomsRes);
            String code = resJson.get("ErrNo").toString();
            if(code.equals("0")){
                List<Integer> roomIds=new ArrayList<>();
                for (AddRoomVO addRoomVO:addRoomVOS) {
                    roomIds.add(Integer.parseInt(addRoomVO.getRoom_id().substring(2)));
                }
                //更新room为已同步
                synchronousHomeMapper.updataHmRoomSyncByRoomId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(),roomIds);
            }else if(code.equals("14001")){
                return "房源已同步";
            }
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
        Log.info("集中式公寓信息,公寓apartmentId：{}",apartmentId);
        return synchronousHomeMapper.selectApartmentIdByApartmentId(apartmentId);
    }

    /**
     * 集中式查询floor同步状态
     * @param apartmentId
     * @return
     */
    @Override
    public List<HomeSyncVO> findFloorsIsSynchronousedByApartmentId(int apartmentId) {
        Log.info("集中式查询floor同步状态,公寓apartmentId：{}",apartmentId);
        List<HomeSyncVO> homeSyncVOList = synchronousHomeMapper.selectFloorsIsSynchronousedByApartmentId(apartmentId, HomeSyncEnum.HOME_SYNC_YUNDING.getCode());
        List<HomeSyncVO> homeSyncVOList1 = synchronousHomeMapper.selectFloorsIsSynchronousedByApartmentId(apartmentId, HomeSyncEnum.HOME_SYNC_UNSYNC.getCode());
        return addList(homeSyncVOList,homeSyncVOList1);
    }

    /**
     * 集中式查询rooms同步状态
     * @param apartmentId
     * @return
     */
    @Override
    public List<HomeSyncVO> findRoomsIsSynchronousedByApartmentId(int apartmentId) {
        Log.info("集中式查询房间同步状态,公寓apartmentId：{}",apartmentId);
        return synchronousHomeMapper.selectRoomsIsSynchronousedByApartmentId(apartmentId);
    }

    /**
     *集中式同步房源byRooms
     * @param apartmentId
     * @param roomIds
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Override
    public String synchronousHousingByRooms(int apartmentId, List<Integer> roomIds) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException {
        Log.info("集中式同步房源,公寓apartmentId:{},房间roomIds：{}",apartmentId,roomIds);
        IWatermeter iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();
        //房源home是否已同步
        String homeReslut = synchronousRooms(apartmentId,roomIds,HouseCatalogEnum.HOUSE_CATALOG_ENUM_VOLGA.getCode(), iWatermeter);
        //如果房源同步失败,返回失败原因
        if(!homeReslut.equals("success")){
            return homeReslut;
        }
        return "success";
    }

    /**
     * 查询分散式未同步的room
     * @param id
     * @return
     */
    @Override
    public List<HomeSyncVO> findHmRoomsIsSynchronousedByUserId(int id) {
        Log.info("查询分散式未同步的room,用户id："+id);
        return synchronousHomeMapper.selectHmRoomsIsSynchronousedByUserId(id);
    }

    /**
     * 同步分散式房间byroomids
     * @param roomId
     * @return
     */
    @Override
    public String synchronousHousingByHmRooms(int roomId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException {
        Log.info("分散式同步房间,房间roomId：{}",roomId);
        IWatermeter iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();
        //查询houseid
        Integer houseId = synchronousHomeMapper.selectHouseIdByRoomId(roomId);
        if (houseId==null){
            return "房间查询不到";
        }
        //同步房源home
        String synchResult = synchronousHome(houseId, HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode(), iWatermeter);
        //如果房源同步失败,返回失败原因
        if(!synchResult.equals("success")){
            return synchResult;
        }
        String homeId = HomeIdNameEnum.HOME_ID_NAME_HM.getCode()+houseId;

        AddRoomVO addRoomVOS=synchronousHomeMapper.findhmRoomByRoomId(roomId);
        //同步room
        String addRoomsRes = iWatermeter.addRoom(homeId,HomeIdNameEnum.HOME_ID_NAME_HM + addRoomVOS.getRoom_id(),addRoomVOS.getRoom_name(),addRoomVOS.getRoom_description());
        JSONObject resJson = null;

        resJson = JSONObject.parseObject(addRoomsRes);

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("同步room失败，{}",msg);
            return "同步room失败，"+msg;
        }

        List<AddRoomVO> roomList=new ArrayList<>();
        roomList.add(addRoomVOS);

        List<Integer> roomIdsList=new ArrayList<>();
        for (AddRoomVO addRoomVO:roomList) {
            roomIdsList.add(Integer.parseInt(addRoomVO.getRoom_id().substring(2)));
        }
        //更新room为已同步
        synchronousHomeMapper.updataHmRoomSyncByRoomId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(),roomIdsList);

        return "success";
    }

    /**
     * 同步分散式房源byhouseidandroomids
     * @param houseId
     * @param roomIds
     */
    @Override
    public String synchronousHousingByHmHomeIdRoomIds(int houseId, List<Integer> roomIds) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException {
        Log.info("分散式同步房间,房源homeId：{},房间roomIds：{}",houseId,roomIds.toArray());
        IWatermeter iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();
        //同步房源home
        String homeReslut = synchronousRooms(houseId,roomIds,HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode(), iWatermeter);
        //如果房源同步失败,返回失败原因
        if(!homeReslut.equals("success")){
            return homeReslut;
        }
        return "success";
    }

    /**
     * 查询集中式roomIdsbyFloorIds
     * @param floorList
     * @return
     */
    @Override
    public List<Integer> findRoomIdsByfloorIds(List<Integer> floorList) {
        return synchronousHomeMapper.selectRoomIdsByfloorIds(floorList);
    }


    /**
     * 同步房源rooms
     * @param houseId
     * @param roomIds
     * @param type
     * @param iWatermeter
     * @return
     * @throws WatermeterException
     */
    public String synchronousRooms(int houseId,List<Integer> roomIds, int type, IWatermeter iWatermeter) throws WatermeterException {
        //同步房源home
        String homeId=null;
        if (type == HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode()) {
            homeId = HomeIdNameEnum.HOME_ID_NAME_HM.getCode() + houseId;
        }else {
            homeId = HomeIdNameEnum.HOME_ID_NAME_JZ.getCode() + houseId;
        }
        //房源home是否已同步
        String synchResult = synchronousHome(houseId, HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode(), iWatermeter);
        //如果房源同步失败,返回失败原因
        if(!synchResult.equals("success")){
            return synchResult;
        }
        //添加room
        //查询所有roombyhouseid
        List<AddRoomVO> addRoomVOS =null;
        if (type == HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode()) {
            addRoomVOS = synchronousHomeMapper.findHmRoomByRoomIds(roomIds);
        }else {
            addRoomVOS = synchronousHomeMapper.findRoomByRoomIds(roomIds);
        }
        List<AddRoomVO> addRoomVOSList =new ArrayList<>();
        if (addRoomVOS != null) {
            for (AddRoomVO addRoomVO : addRoomVOS
                    ) {
                if (type == HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode()) {
                    addRoomVO.setRoom_id(HomeIdNameEnum.HOME_ID_NAME_HM.getCode() + addRoomVO.getRoom_id());
                }else {
                    addRoomVO.setRoom_id(HomeIdNameEnum.HOME_ID_NAME_JZ.getCode() + addRoomVO.getRoom_id());
                }
                addRoomVOSList.add(addRoomVO);
            }

            //添加rooms
            String addRoomsRes = iWatermeter.addRooms(homeId,addRoomVOSList);

            JSONObject resJson2 = JSONObject.parseObject(addRoomsRes);
            String code = resJson2.get("ErrNo").toString();
            //添加成功
            if (code.equals("0")){
                List<Integer> roomIdsList=new ArrayList<>();
                for (AddRoomVO addRoomVO:addRoomVOS) {
                    roomIdsList.add(Integer.parseInt(addRoomVO.getRoom_id().substring(2)));
                }
                if (type == HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode()) {
                    //更新room为已同步
                    synchronousHomeMapper.updataHmRoomSyncByRoomId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(), roomIdsList);
                }else {
                    //更新room为已同步
                    synchronousHomeMapper.updataRoomSyncByRoomId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(), roomIdsList);
                }
            }else {
                return "synchronousRoomfail";
            }
        }
        return "success";
    }

    /**
     * 同步房源home
     * @param houseId
     * @param type
     * @param iWatermeter
     */
    private String synchronousHome(int houseId, int type, IWatermeter iWatermeter) throws WatermeterException {
        String homeId=null;
        if (type == HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode()) {
            homeId = HomeIdNameEnum.HOME_ID_NAME_HM.getCode() + houseId;
        }else {
            homeId = HomeIdNameEnum.HOME_ID_NAME_JZ.getCode() + houseId;
        }
        ///房源home是否已同步
        String state = iWatermeter.findHomeState(homeId);
        JSONObject jsonObject= JSONObject.parseObject(state);
        Object result = jsonObject.get("result");
        String res =null;
        //房源未同步
        if (result == null) {
            AddHomeVo addHomeVo = null;
            if (type == HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode()){
                addHomeVo = synchronousHomeMapper.findHouseByHouseId(houseId);
                addHomeVo.setHome_id(HomeIdNameEnum.HOME_ID_NAME_HM.getCode()+addHomeVo.getHome_id());
                addHomeVo.setHome_type(HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode());
            }else {
                addHomeVo = synchronousHomeMapper.findHouseByApartmentId(houseId);
                addHomeVo.setHome_id(HomeIdNameEnum.HOME_ID_NAME_JZ.getCode()+addHomeVo.getHome_id());
                addHomeVo.setHome_type(HouseCatalogEnum.HOUSE_CATALOG_ENUM_VOLGA.getCode());
            }
            addHomeVo.setCountry("中国");
            //添加房源
            res = iWatermeter.addHome(addHomeVo);
            JSONObject resJson = JSONObject.parseObject(res);
            String code = resJson.get("ErrNo").toString();
            if(!code.equals("0")){
                String msg = resJson.get("ErrMsg").toString();
                Log.error("添加房源失败：{}",msg);
                return "添加房源失败："+msg;
            }
            if (type == HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode()) {
                //更新房源为已同步至云丁
                synchronousHomeMapper.updateHouseSyncByHouseId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(), houseId);
            }else {
                synchronousHomeMapper.updateHomeSyncByApartmentId(HomeSyncEnum.HOME_SYNC_YUNDING.getCode(),houseId);
            }
        }
        return "success";
    }

    /**
     * 合并同步和未同步房源的list
     * @param homeSyncVOList
     * @param homeSyncVOList1
     * @return
     */
    public List<HomeSyncVO> addList(List<HomeSyncVO> homeSyncVOList,List<HomeSyncVO> homeSyncVOList1){
        for (HomeSyncVO homeSyncVO:homeSyncVOList) {
            homeSyncVO.setSynchronous(0);
            if (!homeSyncVOList1.contains(homeSyncVO)){
                homeSyncVO.setSynchronous(1);
                homeSyncVOList1.add(homeSyncVO);
            }
        }
        return homeSyncVOList1;
    }


}
