package com.ih2ome.watermeter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ih2ome.hardware_service.service.model.narcissus.SmartGatewayBind;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeter;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeterRecord;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.peony.watermeterInterface.enums.WATERMETER_FIRM;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.peony.watermeterInterface.vo.AddHomeVo;
import com.ih2ome.peony.watermeterInterface.vo.YunDingResponseVo;
import com.ih2ome.watermeter.dao.WatermeterMapper;
import com.ih2ome.watermeter.service.WatermeterService;
import com.ih2ome.watermeter.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 水表service
 */
@Service
public class WatermeterServiceImpl implements WatermeterService {
    @Resource
    private WatermeterMapper watermeterDao;

    //通过用户create_by_id查询用户房源id
    @Override
    public List<Integer> findRoomIdByUserId(String id) {
        //通过create_by_id查询houseID查询roomId
        //SELECT id FROM room WHERE house_id in (SELECT house_id FROM house_contract WHERE created_by_id = 3);
        List<Integer> roomIds = watermeterDao.findRoomIdByCreatebyid(id);
        return roomIds;
    }

    /**
     * 通过ids查询水表详情列表
     * @param ids
     * @return
     */
    @Override
    public List<WatermeterDetailVO> findWatermetersByids(List<Integer> ids) {
        List<WatermeterDetailVO> watermeterDetailVOS= watermeterDao.finWatermeterByRoomIds(ids);
        return watermeterDetailVOS;
    }

    /**
     * 通过水表id查询水表详情
     * @param id
     * @return
     */
    @Override
    public WatermeterVO findWatermeterByid(String id) {
        //Watermeter watermeter = (Watermeter) watermeterDao.selectByPrimaryKey(id);
        return null;
    }

    /**
     * 通过网关id查询网关详情
     * @param smartGatewayId
     * @return
     */
    @Override
    public WatermeterGatewayDetailVO findGatewaybyId(int smartGatewayId) {
        WatermeterGatewayDetailVO watermeterGatewayDetailVO = watermeterDao.findGatewaybySmartGatewayId(smartGatewayId);
        return watermeterGatewayDetailVO;
    }

    /**
     * 通过网关id查询绑定的水表
     * @param smartGatewayId
     * @return
     */
    @Override
    public List<WatermeterDetailVO> findWatermetersByGatewayId(int smartGatewayId) {
        List<WatermeterDetailVO> watermeterDetailVOS=watermeterDao.findWatermeterByGatewayId(smartGatewayId);
        return watermeterDetailVOS;
    }

    /**
     * 水表抄表读数清单
     *
     * @param smartWatermeterId
     * @param page
     *@param count  @return
     */
    @Override
    public PageResult<SmartWatermeterRecord> findWatermeterRecordByWatermeterId(int smartWatermeterId, int page, int count) {
        // 开启分页
        PageHelper.startPage(page, count);

        // 根据用户名查询，并且按照创建时间降序排列
        /*Example example = new Example(SmartWatermeterRecord.class);
        // 创建查询条件对象
        example.createCriteria().andEqualTo("smartWatermeterId",smartWatermeterId);
        // 实现排序
        example.setOrderByClause("created_at desc");
        List<SmartWatermeterRecord> waterMeterRecordVOS=watermeterDao.selectByExample(example);*/
        List<SmartWatermeterRecord> waterMeterRecordVOS=watermeterDao.findWatermeterRecordByWatermeterId(smartWatermeterId);
        PageInfo<SmartWatermeterRecord> info = new PageInfo<>(waterMeterRecordVOS);

        // 返回分页结果对象
        return new PageResult<>(info.getTotal(), waterMeterRecordVOS);

    }

    /**
     * 查询公寓信息
     * @param id
     * @return
     */
    @Override
    public List<ApartmentVO> findApartmentIdByUserId(int id) {
        //查询公寓信息
        List<ApartmentVO> apartmentVOS=watermeterDao.findApartmentByUserId(id);
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
        /*List<Integer> floorIds = new ArrayList<Integer>();
        for (ApartmentVO apartmentVO : apartmentVOS) {
            floorIds.add(apartmentVO.getFloorId());
        }*/
        List<JZWatermeterDetailVO> jzWatermeterDetailVOS = watermeterDao.findWatermetersByFloorId(floorId);
        return jzWatermeterDetailVOS;

    }

    /**
     * 改水价
     * @param price
     * @return
     */
    @Override
    public Boolean updataWaterPrice(int price,int watermeterId) throws AmmeterException, ClassNotFoundException, IllegalAccessException, InstantiationException{
        int flag= watermeterDao.updataWaterPrice(price,watermeterId);
        //TODO:硬编码
        if(flag==1) {
            return true;
        }
        return false;
    }

    /**
     * 通过公寓id查询水表网关
     * @param apartmentId
     * @return
     */
    @Override
    public List<JZWatermeterGatewayVO> findGatewaysByApartmentId(int apartmentId) {
        List<JZWatermeterGatewayVO> jzWatermeterGatewayVOS = watermeterDao.findGatewayByApartmentId(apartmentId);
        return jzWatermeterGatewayVOS;
    }

    /**
     * 分散式通过用用户id查询水表列表
     * @param id
     * @return
     */
    @Override
    public List<WatermeterDetailVO> findWatermetersByid(int id) {
        return watermeterDao.findWatermetersByUserId(id);
    }

    /**
     * 筛选抄表记录根据时间区间
     * @param watermeterId
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<SmartWatermeterRecord> findWatermeterRecordByWatermeterIdAndTime(int watermeterId, String startTime, String endTime) {
        // 根据用户名查询，并且按照创建时间降序排列
        /*Example example = new Example(SmartWatermeterRecord.class);
        // 创建查询条件对象
        example.createCriteria().andEqualTo("smartWatermeterId",watermeterId);
        example.createCriteria().andBetween("created_at",startTime,endTime);
        // 实现排序
        example.setOrderByClause("created_at desc");
        List<SmartWatermeterRecord> waterMeterRecordVOS=watermeterDao.selectByExample(example);*/
        List<SmartWatermeterRecord> waterMeterRecordVOS=watermeterDao.findWatermeterRecordByWatermeterIdAndTime(watermeterId,startTime,endTime);
        return waterMeterRecordVOS;
    }

    /**
     * 分散式用户房源
     * @param id
     * @return
     */
    @Override
    public List<HouseVO> findHouseByUserId(int id) {
        return watermeterDao.findHouseByUserId(id);
    }

    /**
     * 水表异常记录
     * @param watermeterId
     * @return
     */
    @Override
    public List<ExceptionVO> findWatermeterException(int watermeterId) {
        return watermeterDao.findWatermeterExceptionByWaterId(watermeterId);
    }

    /**
     * 网关异常记录by网关id
     * @param gatewayId
     * @return
     */
    @Override
    public List<ExceptionVO> findWatermeterGatewayException(int gatewayId) {
        return watermeterDao.findWatermeterGatewayExceptionByGatewayId(gatewayId);
    }

    /**
     * 查询房源是否同步by房源id
     * @param homeId
     * @return
     */
    @Override
    public YunDingResponseVo findHomeIsSynchronousedByHomeId(int homeId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException {
        IWatermeter iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();
        String devId = null;
        /*if (type.equals("0")){
            devId =ammeterMannagerVoDao.getDeviceIdByIdWithDispersed(id);
            ammeterMannagerVoDao.updateDevicePayModWithDispersed(id, String.valueOf(pay_mod.getCode()));
        }else{
            devId =ammeterMannagerVoDao.getDeviceIdByIdWithConcentrated(id);
            ammeterMannagerVoDao.updateDevicePayModWithConcentrated(id, String.valueOf(pay_mod.getCode()));
        }
        iAmmeter.updatePayMod(devId,pay_mod);*/

        String res= iWatermeter.findHomeState(String.valueOf(homeId));
        if (res == null){
            return null;
        }
        YunDingResponseVo jsonObject=JSONObject.parseObject(res,YunDingResponseVo.class);
        return jsonObject;
    }

    /**
     * 查询实时抄表记录
     * @param watermeterId
     * @return
     */
    @Override
    public int findWatermeterLastAmountByWatermeterId(int watermeterId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException{
        //查询水表uuid，和供应商
        WatermeterRecordParamsVo params=watermeterDao.findWatermeterRecordParamsByWatermeterId(watermeterId);
        //查询水表实时抄表记录
        IWatermeter iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();
        String res= iWatermeter.readWatermeter(params.getUuid(),params.getManufactory());

        YunDingResponseVo jsonObject=JSONObject.parseObject(res,YunDingResponseVo.class);
        JSONObject result = jsonObject.getResult();
        return 0;
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
        AddHomeVo addHomeVo = watermeterDao.findHouseByHouseId(houseId);

        addHomeVo.setHome_id("hm"+addHomeVo.getHome_id());
        addHomeVo.setHome_type(1);
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
        if(!home_id.equals(String.valueOf(houseId))){
            return null;
        }
        //添加room
        //查询所有roombyhouseid
        List<AddRoomVO> addRoomVOS=watermeterDao.findRoomByHouseId(houseId);
        List<AddRoomVO> addRoomVOSList =new ArrayList<>();
        for (AddRoomVO addRoomVO:addRoomVOS
             ) {
            addRoomVO.setRoom_id("hm" + addRoomVO.getRoom_id());
            addRoomVOSList.add(addRoomVO);
        }

        String[] strings = new String[addRoomVOSList.size()];
        strings=addRoomVOSList.toArray(strings);

        String addRoomsRes = iWatermeter.addRooms("hm"+houseId,strings);

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
        AddHomeVo addHomeVo = watermeterDao.findHouseByApartmentId(apartmentId);
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
        List<AddRoomVO> addRoomVOS=watermeterDao.findRoomByApartmentId(apartmentId);
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
            AddHomeVo addHomeVo = watermeterDao.findHouseByApartmentId(floorId);
            //TODO:硬编码
            addHomeVo.setHome_type(1);
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
        //添加room
        //查询所有roombyFloorId
        List<AddRoomVO> addRoomVOS=watermeterDao.findRoomByFloorId(floorId);
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

    /**
     * 更新水表抄表读数
     * @param uuid
     * @param amount
     * @param time
     */
    @Override
    public void updataWaterLastAmount(String uuid, int amount, int time) {
        watermeterDao.updataWaterLastAmount(uuid, amount, time);
    }

    /**
     * 查询floorIdbyRoomid
     * @param room_id
     * @return
     */
    @Override
    public Long findFloorIdByRoomId(Long room_id) {
        return  watermeterDao.findFloorIdByRoomId(room_id);
    }

    /**
     * 添加水表
     * @param smartWatermeter
     */
    @Override
    public void createSmartWatermeter(SmartWatermeter smartWatermeter) {
        watermeterDao.addSmartWatermeter(smartWatermeter);
    }

    /**
     * 添加网关绑定
     * @param smartGatewayBind
     */
    @Override
    public void addSmartGatewayBind(SmartGatewayBind smartGatewayBind) {
        watermeterDao.addSmartGatewayBind(smartGatewayBind);
    }

    /**
     * 查询HouseCreatedByByHouseId
     * @param houseId
     * @return
     */
    @Override
    public int findHouseCreatedByByHouseId(Long houseId) {
        return watermeterDao.selectHouseCreatedByByHouseId(houseId);
    }

    /**
     * 查询HouseCreatedByByapartmentId
     * @param apartmentId
     * @return
     */
    @Override
    public int findApartmentCreatedByByApartmentId(Long apartmentId) {
        return watermeterDao.selectApartmentCreatedByByApartmentId(apartmentId);
    }

    /**
     * 查询水表idByuuid
     * @param uuid
     * @return
     */
    @Override
    public int findWatermeterIdByUuid(String uuid) {
        SmartWatermeter watermeter=watermeterDao.findWatermetersByUuId(uuid);
        return watermeter.getSmartWatermeterId();
    }


}
