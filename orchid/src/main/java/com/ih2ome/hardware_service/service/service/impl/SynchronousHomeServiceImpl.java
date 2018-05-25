package com.ih2ome.hardware_service.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.hardware_service.service.dao.SmartLockDao;
import com.ih2ome.hardware_service.service.dao.SynchronousHomeMapper;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.peony.smartlockInterface.factory.SmartLockOperateFactory;
import com.ih2ome.sunflower.entity.narcissus.*;
import com.ih2ome.sunflower.model.backup.HomeVO;
import com.ih2ome.sunflower.model.backup.RoomVO;
import com.ih2ome.sunflower.vo.pageVo.enums.*;
import com.ih2ome.hardware_service.service.service.SynchronousHomeService;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartHouseMappingVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockGateWayHadBindInnerLockVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockGatewayAndHouseInfoVO;
import com.ih2ome.sunflower.vo.pageVo.watermeter.ApartmentVO;
import com.ih2ome.sunflower.vo.pageVo.watermeter.HomeSyncVO;
import com.ih2ome.sunflower.vo.pageVo.watermeter.HouseVO;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.enums.SmartLockFirmEnum;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.enums.YunDingHomeTypeEnum;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingDeviceInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingHomeInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingRoomInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.enums.WATERMETER_FIRM;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.AddHomeVo;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.AddRoomVO;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.YunDingResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SynchronousHomeServiceImpl implements SynchronousHomeService{

    @Resource
    private SynchronousHomeMapper synchronousHomeMapper;

    private static final Logger Log = LoggerFactory.getLogger(SynchronousHomeServiceImpl.class);
    @Resource
    private SmartLockDao smartLockDao;
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

    @Override
    public Map<String, List<HomeVO>> serchWater(String userId, String type, String factoryType)throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException {
        Log.info("==========查询房源信息userId:{}",userId);
        //查询第三方房源信息
        SmartLockFirmEnum smartLockFirmEnum = SmartLockFirmEnum.getByCode(factoryType);
        if (smartLockFirmEnum == null) {
            throw new SmartLockException("该厂商不存在");
        }
        //第三方房源信息(包括了分散式和集中式)
        List<HomeVO> thirdHomeList = new ArrayList<HomeVO>();
        if (smartLockFirmEnum != null && smartLockFirmEnum.getCode().equals(SmartLockFirmEnum.YUN_DING.getCode())) {
            ISmartLock iSmartLock = (ISmartLock) Class.forName(smartLockFirmEnum.getClazz()).newInstance();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userId", userId);
            String result = iSmartLock.searchHomeInfo(params);
            List<YunDingHomeInfoVO> yunDingHomes = JSONObject.parseArray(result, YunDingHomeInfoVO.class);
            //处理云丁的房源数据，将房源下房间中没有设备的房间移除
            removeNoDevicesRoom(yunDingHomes);

            for (YunDingHomeInfoVO yunDingHomeInfoVO : yunDingHomes) {
                HomeVO homeVO = YunDingHomeInfoVO.toH2ome(yunDingHomeInfoVO);
                thirdHomeList.add(homeVO);
            }

        }
        //水滴的房源信息
        List<HomeVO> localHomeList = new ArrayList<>();
        List<String> list;
        //判断是分散式(0是集中式，1是分散式)
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            //查询子账号信息
            String employerId=smartLockDao.queryEmployer(userId);
            if(employerId==null){
                list=smartLockDao.findDispersedHomesAndPublicZone(userId);
                //查询没有公共区域的分散式房源id并给它添加公共区域
                if(list.size()!=0){
                    for(String roomId:list){
                        smartLockDao.dispersiveAddition(roomId);
                    }
                }
                localHomeList = smartLockDao.findDispersedHomes(userId);
            }else{
                list=smartLockDao.queryDispersedHomesAndPublicZone(employerId);
                if(list.size()!=0){
                    for(String roomId:list){
                        smartLockDao.dispersiveAddition(roomId);
                    }
                }
                //查询子账号可控房源
                List<String> housesIdList=smartLockDao.queryEmployerHouses(employerId);
                for(String housesId : housesIdList){
                    localHomeList.addAll(smartLockDao.findDispersedSubaccountHomes(housesId));
                }
            }
            Iterator<HomeVO> iterator = thirdHomeList.iterator();
            while (iterator.hasNext()) {
                HomeVO homeVO = iterator.next();
                if (!homeVO.getHomeType().equals(YunDingHomeTypeEnum.DISPERSED.getCode())) {
                    iterator.remove();
                }
            }
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            //查询子账号信息
            String employerapatmentsid=smartLockDao.findEmployer(userId);
            if(employerapatmentsid==null){
                //查询没有公共区域的集中式房源id并给它添加公共区域
                list=smartLockDao.centralizedFindDispersedHomes(userId);
                if(list.size()!=0){
                    for(String roomId:list){
                        smartLockDao.centralizedAddition(roomId);
                    }
                }
                localHomeList = smartLockDao.findConcentrateHomes(userId);
            }else{
                list=smartLockDao.centralizedqueryDispersedHomes(employerapatmentsid);
                if(list.size()!=0){
                    for(String roomId:list){
                        smartLockDao.centralizedAddition(roomId);
                    }
                }
                //子账号，根据子账号查询apatment
                List<String> apatmentIdList=smartLockDao.findEmployerApatments(employerapatmentsid);
                for(String apatmentId : apatmentIdList){
                    localHomeList.addAll(smartLockDao.findCentralizedHomes(apatmentId));
                }
            }
            Iterator<HomeVO> iterator = thirdHomeList.iterator();
            while (iterator.hasNext()) {
                HomeVO homeVO = iterator.next();
                if (!homeVO.getHomeType().equals(YunDingHomeTypeEnum.CONCENTRAT.getCode())) {
                    iterator.remove();
                }
            }

        }
        //房间关联数据处理
        for (HomeVO localHomeVO : localHomeList) {
            List<RoomVO> localRooms = localHomeVO.getRooms();
            for (RoomVO localRoom : localRooms) {
                String thirdRoomId = localRoom.getThirdRoomId();
                if (thirdRoomId != null) {
                    for (HomeVO thirdHomeVO : thirdHomeList) {
                        List<RoomVO> thirdRooms = thirdHomeVO.getRooms();
                        //遍历判断第三方房源是否关联并添加关联信息
                        for(RoomVO thirdRoom:thirdRooms ){
                            if (thirdRoomId.equals(thirdRoom.getThirdRoomId())) {
                                thirdRoom.setRoomId(localRoom.getRoomId());
                                thirdRoom.setRoomName(localRoom.getRoomName());
                                thirdRoom.setDataType(localRoom.getDataType());
                                thirdRoom.setRoomAssociationStatus("1");
                                break;
                            }
                        }
                        Iterator<RoomVO> iterator = thirdRooms.iterator();
                        while (iterator.hasNext()) {
                            RoomVO roomVO = iterator.next();
                            if (thirdRoomId.equals(roomVO.getThirdRoomId())) {
                                localRoom.setThirdRoomName(roomVO.getThirdRoomName());
                                localHomeVO.setThirdHomeId(thirdHomeVO.getHomeId());
                                thirdHomeVO.setLocalHomeId(localHomeVO.getHomeId());
//                                roomVO.setRoomAssociationStatus("1");
//                                iterator.remove();
                                break;
                            }
                        }
                    }
                }
            }
        }
        Map<String, List<HomeVO>> map = new HashMap<>();
        map.put("thirdHomeList", thirdHomeList);
        map.put("localHomeList", localHomeList);
        return map;
    }
    private IWatermeter getIWatermeter(){
        IWatermeter iWatermeter = null;
        try {
            iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();
        } catch (InstantiationException e) {
            Log.error("获取水表类失败",e);
        } catch (IllegalAccessException e) {
            Log.error("获取水表类失败",e);
        } catch (ClassNotFoundException e) {
            Log.error("获取水表类失败",e);
        }
        return iWatermeter;
    }
    /**
     * 关联
     * @param smartHouseMappingVO
     * @throws SmartLockException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ParseException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmAssociation(SmartHouseMappingVO smartHouseMappingVO) throws SmartLockException, ClassNotFoundException, IllegalAccessException, InstantiationException, ParseException {
        ///// 1.1 获取数据
        //集中式或者分散式类型
        String type = smartHouseMappingVO.getType();
        //用户id
        String userId = smartHouseMappingVO.getUserId();
        //房屋或者公共区域类型
        String dataType = smartHouseMappingVO.getDataType();
        //第三方房源Id
        String thirdHomeId = smartHouseMappingVO.getThirdHomeId();
        //第三方房间Id
        String thirdRoomId = smartHouseMappingVO.getThirdRoomId();
        //获取本地公共区域或房间的Id
        String roomId = smartHouseMappingVO.getRoomId();
        //获得厂商
        String providerCode = smartHouseMappingVO.getFactoryType();

        String gateWayuuid=smartHouseMappingVO.getGateWayuuid();

        String Uuid =smartHouseMappingVO.getUuid();
        String publicZoneId = null;

        //1.2 获取公区
        //判断是否是公共区域
        if (HouseMappingDataTypeEnum.PUBLICZONE.getCode().equals(dataType)) {
            publicZoneId = roomId;
        }
        //判断是否是房间
        else if (HouseMappingDataTypeEnum.ROOM.getCode().equals(dataType)) {
            //判断是分散式
            if (HouseStyleEnum.DISPERSED.getCode().equals(type)) {
                //查询该房间所属房源的公共区域
                publicZoneId = smartLockDao.findDispersedPublicZoneByRoomId(roomId);
                //判断是集中式
            } else if (HouseStyleEnum.CONCENTRAT.getCode().equals(type)) {
                //查询该房间所属房源的公共区域
                publicZoneId = smartLockDao.findConcentratePublicZoneByRoomId(roomId);
            } else {
                throw new SmartLockException("参数异常");
            }
        } else {
            throw new SmartLockException("参数异常");
        }



        List<SmartLockGateWayHadBindInnerLockVO> gatewayBindInnerLocks = smartLockDao.findGatewayBindInnerLock(type, publicZoneId, providerCode);
        IWatermeter iWatermeter = getIWatermeter();
        try {
            Date day=new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SmartDeviceV2 smartDeviceV2=new SmartDeviceV2();
            if(publicZoneId==roomId){
                saveGatWay(iWatermeter,Uuid,userId,type,publicZoneId,providerCode);
            }else {
                String watermeterInfo=iWatermeter.getWatermeterInfo(Uuid,providerCode);
                JSONObject resJson = JSONObject.parseObject(watermeterInfo);
                String info =  resJson.getString("info");
                JSONObject jsonObject = JSONObject.parseObject(info);
                String meter_type = jsonObject.getString("meter_type");
                String name=null;
                if("1".equals(meter_type)){
                    name="冷水表";
                }else if("2".equals(meter_type)){
                    name="热水表";
                }
                String onoff = jsonObject.getString("onoff");
                String manufactory=jsonObject.getString("manufactory");
                smartDeviceV2.setBrand("dding");
                smartDeviceV2.setConnectionStatus(onoff);
                smartDeviceV2.setConnectionStatusUpdateTime(df.format(day));
                smartDeviceV2.setCreatedBy(userId);
                smartDeviceV2.setHouseCatalog(type);
                smartDeviceV2.setName(name);
                smartDeviceV2.setProviderCode(providerCode);
                smartDeviceV2.setRoomId(roomId);
                smartDeviceV2.setSmartDeviceType("2");
                smartDeviceV2.setThreeId(Uuid);
                //新增水表关联记录
                smartLockDao.addSmartDevice(smartDeviceV2);
                SmartWatermeter smartWatermeter=new SmartWatermeter();
                smartWatermeter.setSmartWatermeterId(Long.parseLong(smartDeviceV2.getSmartDeviceId()));
                smartWatermeter.setCreatedAt(new Date());
                smartWatermeter.setCreatedBy(Long.parseLong(userId));
                smartWatermeter.setUpdatedAt(new Date());
                smartWatermeter.setUpdatedBy(Long.parseLong(userId));
                smartWatermeter.setRoomId(Long.parseLong(roomId));
                smartWatermeter.setHouseCatalog(Long.parseLong(type));
                smartWatermeter.setMeter(meter_type);
                smartWatermeter.setUuid(Uuid);
                smartWatermeter.setOnoffStatus(Long.parseLong(onoff));
                smartWatermeter.setManufactory(manufactory);
                smartLockDao.saveWaterMeter(smartWatermeter);
                String smartGatWayid=smartLockDao.querySmartGatWayid(publicZoneId);
                if(smartGatWayid==null){
                    saveGatWay(iWatermeter,gateWayuuid,userId,type,publicZoneId,providerCode);
                }
                smartLockDao.addSmartDeviceBind(smartDeviceV2.getSmartDeviceId(), smartGatWayid);
            }
        } catch (WatermeterException e) {
            e.printStackTrace();
        }

        //3.3 门锁房间关联
        SmartHouseMappingVO houseMapping = SmartHouseMappingVO.toH2ome(smartHouseMappingVO);
        //查询该关联关系原先是否存在
        SmartHouseMappingVO houseMappingRecord = smartLockDao.findHouseMappingRecord(houseMapping);
        //该记录存在，修改该映射记录
        if (houseMappingRecord != null) {
            smartLockDao.updateAssociation(houseMapping);
        } else {
            smartLockDao.addAssociation(houseMapping);
        }
    }

    /**
     * 取消关联
     * @param smartHouseMappingVO
     * @throws SmartLockException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelRelation(SmartHouseMappingVO smartHouseMappingVO) throws SmartLockException {
        String type = smartHouseMappingVO.getType();
        SmartHouseMappingVO houseMapping = SmartHouseMappingVO.toH2ome(smartHouseMappingVO);
        String dataType = houseMapping.getDataType();
        List<SmartHouseMappingVO> roomMappingList = new ArrayList<SmartHouseMappingVO>();
        //判断取消关联的是公共区域之间的关联
        if (HouseMappingDataTypeEnum.PUBLICZONE.getCode().equals(dataType)) {
            //判断是分散式
            if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
                roomMappingList = smartLockDao.findDispersedRoomMappingByPublicZone(houseMapping);
                //判断是集中式
            } else {
                roomMappingList = smartLockDao.findConcentrateRoomMappingByPublicZone(houseMapping);
            }
            //取消该公共区域所属房源下的房间的关联
            for (SmartHouseMappingVO smartHouseMappingVO1 : roomMappingList) {
                smartLockDao.cancelAssociation(smartHouseMappingVO1);
                //清除该房间下的设备
                smartLockDao.clearDevicesByRoomId(smartHouseMappingVO1.getHouseCatalog(),
                        smartHouseMappingVO1.getH2omeId(), smartHouseMappingVO1.getProviderCode());
            }
            //取消公共区域的关联
            smartLockDao.cancelAssociation(houseMapping);
            //清除该公共区域下的设备
            smartLockDao.clearDevicesByPublicZoneId(houseMapping.getHouseCatalog(),
                    houseMapping.getH2omeId(), houseMapping.getProviderCode());
            //判断取消关联的是房间之间的关联
        } else if (HouseMappingDataTypeEnum.ROOM.getCode().equals(dataType)) {
            smartLockDao.cancelAssociation(houseMapping);
            smartLockDao.clearDevicesByRoomId(houseMapping.getHouseCatalog(),
                    houseMapping.getH2omeId(), houseMapping.getProviderCode());
        }

    }



    public void saveGatWay(IWatermeter iWatermeter,String Uuid,String userId,String type,String publicZoneId,String providerCode){
        String res= null;
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            res = iWatermeter.getWaterGatewayInfo(Uuid);
        } catch (WatermeterException e) {
            e.printStackTrace();
        }
        JSONObject resJsonObject = JSONObject.parseObject(res);
        String resInfo =  resJsonObject.getString("info");
        JSONObject json = JSONObject.parseObject(resInfo);
        SmartGatewayV2 smartGatewayV2=new SmartGatewayV2();
        String off=json.getString("onoff");
        String description=json.getString("description");
        String mtime=json.getString("mtime");
        String createTime=json.getString("ctime");
        SmartDeviceV2 smartDevice=new SmartDeviceV2();
        smartDevice.setBrand("dding");
        smartDevice.setConnectionStatus(off);
        smartDevice.setConnectionStatusUpdateTime(df.format(day));
        smartDevice.setCreatedBy(userId);
        smartDevice.setHouseCatalog(type);
        smartDevice.setName(description);
        smartDevice.setProviderCode(providerCode);
        smartDevice.setPublicZoneId(publicZoneId);
        smartDevice.setSmartDeviceType("5");
        smartDevice.setThreeId(Uuid);
        smartLockDao.addSmartDevice(smartDevice);
        smartGatewayV2.setSmartGatewayId(smartDevice.getSmartDeviceId());
        smartGatewayV2.setUuid(Uuid);
        smartGatewayV2.setInstallTime(createTime);
        smartGatewayV2.setBrand("dding");
        smartGatewayV2.setModel(description);
        smartLockDao.saveGatWay(smartGatewayV2);
    }

    //处理云丁的房源数据，将房源下房间中没有设备的房间移除
    private void removeNoDevicesRoom(List<YunDingHomeInfoVO> yunDingHomes) {
        for (YunDingHomeInfoVO yunDingHomeInfoVO : yunDingHomes) {
            List<YunDingRoomInfoVO> rooms = yunDingHomeInfoVO.getRooms();
            List<YunDingDeviceInfoVO> devices = yunDingHomeInfoVO.getDevices();
            Iterator<YunDingRoomInfoVO> roomIterator = rooms.iterator();
            while (roomIterator.hasNext()) {
                boolean flag = true;
                YunDingRoomInfoVO roomInfo = roomIterator.next();
                for (YunDingDeviceInfoVO deviceInfo : devices) {
                    if (roomInfo.getRoomId().equals(deviceInfo.getRoomId())&&deviceInfo.getUuid()!=null&&(deviceInfo.getType().equals("watermeter")||deviceInfo.getType().equals("water_gateway"))) {
//                        Log.info("========roomInfo:{}", roomInfo);
//                        Log.info("========deviceInfo:{}", deviceInfo);
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    roomIterator.remove();
                }
            }
        }
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
        String synchResult =null;
        if (type == HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode()) {
            homeId = HomeIdNameEnum.HOME_ID_NAME_HM.getCode() + houseId;
            //房源home是否已同步
            synchResult = synchronousHome(houseId, HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode(), iWatermeter);
        }else {
            homeId = HomeIdNameEnum.HOME_ID_NAME_JZ.getCode() + houseId;
            //房源home是否已同步
            synchResult = synchronousHome(houseId, HouseCatalogEnum.HOUSE_CATALOG_ENUM_VOLGA.getCode(), iWatermeter);
        }
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
