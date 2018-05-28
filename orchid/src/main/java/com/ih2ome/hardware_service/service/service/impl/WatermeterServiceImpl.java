package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.hardware_service.service.dao.SmartLockDao;
import com.ih2ome.hardware_service.service.dao.WatermeterMapper;
import com.ih2ome.sunflower.entity.narcissus.*;
import com.ih2ome.hardware_service.service.service.WatermeterService;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.enums.WATERMETER_FIRM;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.sunflower.vo.pageVo.watermeter.*;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 水表service
 */
@Service
public class WatermeterServiceImpl implements WatermeterService {

    private static final Logger Log = LoggerFactory.getLogger(WatermeterServiceImpl.class);

    @Resource
    private WatermeterMapper watermeterDao;
    @Resource
    private SmartLockDao smartLockDao;

    //通过用户create_by_id查询用户房源id
    @Override
    public List<Integer> findRoomIdByUserId(String id) {
        Log.info(" 查询用户房源id，用户id：{}",id);
        //通过create_by_id查询houseID查询roomId
        List<Integer> roomIds = watermeterDao.findRoomIdByCreatebyid(id);
        return roomIds;
    }

    /**
     * 通过ids查询水表详情列表
     * @param ids
     * @return
     */
    @Override
    public List<HMWatermeterListVO> findWatermetersByids(List<Integer> ids) {
        Log.info(" 通过水表id查询水表详情，水表ids：{}",ids);
        List<HMWatermeterListVO> watermeterDetailVOS= watermeterDao.finWatermeterByRoomIds(ids);
        return watermeterDetailVOS;
    }

    /**
     * 通过水表Id查询水表详情
     * @param waterId 水表Id
     * @return 水表详情
     */
    @Override
    public WatermeterVO getWatermeterById(int waterId) {
        Log.info(" 通过水表id查询水表详情，waterId：{}",waterId);
        WatermeterVO smartWatermeter = watermeterDao.getWatermeterById(waterId);
        return smartWatermeter;
    }

    /**
     * 通过网关id查询网关详情
     * @param smartGatewayId
     * @return
     */
    @Override
    public WatermeterGatewayDetailVO findGatewaybyId(int smartGatewayId) {
        Log.info("通过网关id查询网关详情，网关smartGatewayId：{}",smartGatewayId);
        WatermeterGatewayDetailVO watermeterGatewayDetailVO = watermeterDao.findGatewaybySmartGatewayId(smartGatewayId);
        return watermeterGatewayDetailVO;
    }
    /**
     * 通过网关id查询网关详情
     * @param smartGatewayId
     * @return
     */
    @Override
    public WatermeterGatewayDetailVO findhmGatewaybyId(int smartGatewayId) {
        Log.info("通过网关id查询网关详情，网关smartGatewayId：{}",smartGatewayId);
        WatermeterGatewayDetailVO watermeterGatewayDetailVO = watermeterDao.findhmGatewaybySmartGatewayId(smartGatewayId);
        return watermeterGatewayDetailVO;
    }

    /**
     * 查询水表idbygatewayId
     * @param uuid
     * @return
     */
    @Override
    public List<String> findWatermeterIdByGatewayUuid(String uuid) {
        return watermeterDao.selectWatermeterIdByGatewayUuid(uuid);
    }

    /**
     * 通过网关id查询绑定的水表
     * @param smartGatewayId
     * @return
     */
    @Override
    public List<HMWatermeterListVO> findWatermetersByGatewayId(int smartGatewayId) {
        Log.info("通过网关id查询绑定的水表，水表网关smartGatewayId：{}",smartGatewayId);
        List<HMWatermeterListVO> watermeterDetailVOS=watermeterDao.findWatermeterByGatewayId(smartGatewayId);
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
        Log.info("查询水表抄表记录,水表smartWatermeterId：{}",smartWatermeterId);
        // 开启分页
        PageHelper.startPage(page, count);

        // 根据用户名查询，并且按照创建时间降序排列
        List<SmartWatermeterRecord> waterMeterRecordVOS=watermeterDao.findWatermeterRecordByWatermeterId(smartWatermeterId);
        PageInfo<SmartWatermeterRecord> info = new PageInfo<>(waterMeterRecordVOS);

        // 返回分页结果对象
        return new PageResult<>(info.getTotal(), waterMeterRecordVOS);

    }

    /**
     * 查询集中式水表列表通过楼层id
     * @param floorId
     * @return
     */
    @Override
    public List<JZWatermeterDetailVO> findWatermetersByFloorId(int floorId) {
        Log.info("查询集中式水表列表,楼层floorId：{}",floorId);
        //通过楼层ids查询水表
        List<JZWatermeterDetailVO> jzWatermeterDetailVOS = watermeterDao.findWatermetersByFloorId(floorId);
        return jzWatermeterDetailVOS;

    }

    /**
     * 根据第三方id查询网关id
     * @param homeid
     * @return
     */
    @Override
    public String findGateWay(String homeid) {
       String gateWay = watermeterDao.findGateWay(homeid);
        return gateWay;
    }

    /**
     * 改水价
     * @param price
     * @return
     */
    @Override
    public Boolean updataWaterPrice(int price,int watermeterId) throws AmmeterException, ClassNotFoundException, IllegalAccessException, InstantiationException{
        Log.info("修改水价,水表id：{}",watermeterId);
        Integer flag= watermeterDao.updataWaterPrice(price,watermeterId);

        if(flag != null) {
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
        Log.info("查询水表网关,公寓apartmentId：{}"+apartmentId);
        List<JZWatermeterGatewayVO> jzWatermeterGatewayVOS = watermeterDao.findGatewayByApartmentId(apartmentId);
        return jzWatermeterGatewayVOS;
    }

    /**
     * 分散式通过用用户id查询水表列表
     * @param id
     * @return
     */
    @Override
    public List<HMWatermeterListVO> findWatermetersByid(int id) {
        Log.info("分散式查询水表列表,用户id：{}",id);
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
        Log.info("水表抄表记录筛选,水表watermeterId：{},开始时间：{},截止时间：{}",watermeterId,startTime,endTime);
        // 根据用户名查询，并且按照创建时间降序排列
        List<SmartWatermeterRecord> waterMeterRecordVOS=watermeterDao.findWatermeterRecordByWatermeterIdAndTime(watermeterId,startTime,endTime);
        return waterMeterRecordVOS;
    }



    /**
     * 水表异常记录
     * @param watermeterId
     * @return
     */
    @Override
    public List<ExceptionVO> findWatermeterException(int watermeterId) {
        Log.info("水表异常记录,水表watermeterId：{}",watermeterId);
        return watermeterDao.findWatermeterExceptionByWaterId(watermeterId);
    }

    /**
     * 网关异常记录by网关id
     * @param gatewayId
     * @return
     */
    @Override
    public List<ExceptionVO> findWatermeterGatewayException(int gatewayId) {
        Log.info("网关异常记录,网关id：",gatewayId);
        return watermeterDao.findWatermeterGatewayExceptionByGatewayId(gatewayId);
    }


    /**
     * 发送抄表请求
     * @param watermeterId
     * @return
     */
    @Override
    public String readWatermeterLastAmountByWatermeterId(int watermeterId, String userId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException{
        Log.info("水表watermeterId：{}",watermeterId);
        //查询水表uuid，和供应商
        WatermeterRecordParamsVo params=watermeterDao.findWatermeterRecordParamsByWatermeterId(watermeterId);
        //查询水表实时抄表记录
        IWatermeter iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();
        String reslut= iWatermeter.readWatermeter(params.getUuid(),params.getManufactory(), userId);

        return reslut;
    }




    /**
     * 更新水表抄表读数
     * @param uuid
     * @param amount
     * @param time
     */
    @Override
    public void updataWaterLastAmount(String uuid, int amount, Long time) {
        Log.info("更新水表读数,水表uuid:{},水表读数:{},更新时间：{}",uuid,amount,time);
        watermeterDao.updataWaterLastAmount(uuid, amount, time);
    }


    /**
     * 添加水表
     * @param smartWatermeter
     */
    @Override
    public void createSmartWatermeter(SmartWatermeter smartWatermeter) {
        Log.info("添加水表,水表信息smartWatermeter：{}",smartWatermeter.toString());
        watermeterDao.addSmartWatermeter(smartWatermeter);
    }

    /**
     * 添加网关绑定
     * @param smartGatewayBind
     */
    @Override
    public void addSmartGatewayBind(SmartGatewayBind smartGatewayBind) {
        Log.info("添加网关绑定,网关信息:{}",smartGatewayBind.toString());
        watermeterDao.addSmartGatewayBind(smartGatewayBind);
    }

    /**
     * 查询HouseCreatedByByHouseId
     * @param houseId
     * @return
     */
    @Override
    public int findHouseCreatedByByHouseId(Long houseId) {
        Log.info("查询房源用户id，房源houseId：{}",houseId);
        return watermeterDao.selectHouseCreatedByByHouseId(houseId);
    }

    /**
     * 查询HouseCreatedByByapartmentId
     * @param apartmentId
     * @return
     */
    @Override
    public Integer findApartmentCreatedByByApartmentId(Long apartmentId) {
        Log.info("查询房源用户id，公寓apartmentId：{}",apartmentId);
        return watermeterDao.selectApartmentCreatedByByApartmentId(apartmentId);
    }

    /**
     * 查询水表idByuuid
     * @param uuid
     * @return
     */
    @Override
    public SmartWatermeter getWatermeterByUuId(String uuid) {
        Log.info("查询水表,水表uuid：{}"+uuid);
        return watermeterDao.getWatermeterByUuId(uuid);
    }

    @Override
    public SmartWatermeter getWatermeterByDeviceId(int deviceId) {
        return watermeterDao.getWatermeterByDeviceId(deviceId);
    }

    /**
     * 查询水表读数by水表id
     * @param watermeterId
     * @return
     */
    @Override
    public Integer findWatermeterLastAmountByWatermeterId(int watermeterId) {
        Log.info("查询水表读数,水表watermeterId：{}",watermeterId);
        return watermeterDao.findWatermeterAmountByWatermeterId(watermeterId);
    }

    /**
     * 更新水表在线离线状态
     * @param uuid
     * @param code
     */
    @Override
    public void updataWatermerterOnoffStatus(String uuid, Integer code) {
        Log.info("更新水表在线离线状态，水表uuid：{},状态code：{}",uuid,code);
        watermeterDao.updataWatermerterOnoffStatusByUuid(uuid,code);
    }

    /**
     * 查询水表所有的uuidandManufactory
     * @return
     */
    @Override
    public List<UuidAndManufactoryVO> findWatermeterUuidAndManufactory() {
        Log.info("查询水表所有的uuid和Manufactory");
        return watermeterDao.selectWatermeterUuidAndManufactory();
    }

    /**
     * 最近一次抄表时间
     * @param uuid
     * @return
     */
    @Override
    public Timestamp findWatermeterMeterUpdatedAt(String uuid) {
        Log.info("最近一次抄表时间,水表uuid:{}",uuid);
        return watermeterDao.selectWatermeterMeterUpdatedAt(uuid);
    }

    /**
     * 集中式水表listby网关id
     * @param smartGatewayId
     * @return
     */
    @Override
    public List<JZWaterMeterVO> findJzWatermetersByGatewayId(int smartGatewayId) {
        Log.info("集中式水表列表,网关smartGatewayId:{}",smartGatewayId);
        return watermeterDao.selectJzWatermetersByGatewayId(smartGatewayId);
    }

    /**
     * 分散式水表网关详情
     * @param userId
     * @return
     */
    @Override
    public List<JZWatermeterGatewayVO> findGatewaysByUserId(int userId) {
        Log.info("分散式网关详情,userId：{}",userId);
        return watermeterDao.selectGatewaysByUserId(userId);
    }

    /**
     * 查询水表在线状态byuuid
     * @param uuid
     * @return
     */
    @Override
    public Integer findWatermeterOnOffStatusByUuid(String uuid) {
        Log.info("查询水表在线状态,水表uuid：{}",uuid);
        return watermeterDao.selectWatermeterOnOffStatusByUuid(uuid);
    }

    /**
     * 查询所有水表id
     * @return
     */
    @Override
    public List<Integer> findAllWatermeterIds() {
        Log.info("查询所有水表id");
        return watermeterDao.selectAllWatermeterIds();
    }

    /**
     * 查询水表月初抄表读数
     * @param watermeterId
     * @return
     */
    @Override
    public Integer findMeterAmountByWatermeterId(Integer watermeterId) {
        Log.info("查询水表月初抄表读数，水表watermeterId：{}",watermeterId);
        return watermeterDao.selectMeterAmountByWatermeterId(watermeterId);
    }

    /**
     * 更新水表月初读数
     * @param watermeterId
     * @param meterAmount
     */
    @Override
    public void updataWatermeterMeterAmount(long watermeterId, Integer meterAmount) {
        Log.info("更新水表月初读数，水表watermeterId：{},水表读数meterAmount：{}",watermeterId,meterAmount);
        watermeterDao.updataWatermeterMeterAmount(watermeterId,meterAmount);
    }

    /**
     * 根据userId和第三方
     * 查询已绑定设备（包括网关）的集中式房源
     * @param userId 用户Id
     * @param brand 第三方标识符
     * @return 房源列表
     */
    @Override
    public List<HomeVO> getApartmentListByUserId(int userId, String brand) {
        Log.info("查询已绑定设备（包括网关）的房源,userId：{}, brand:{}", userId, brand);

        List<String> userIdList = smartLockDao.findUserId(String.valueOf(userId));
        userIdList.add(String.valueOf(userId));
        String userIds = StringUtils.join(userIdList.toArray(), ",");
        return watermeterDao.getApartmentListByUserId(userIds, brand);
    }

    /**
     * 集中式：根据公寓Id查询公寓内楼层水表数
     * @param apartmentId 公寓Id
     * @return 公寓水表数 + 各楼层水表数
     */
    @Override
    public List<FloorVO> getFloorWithWater(int apartmentId){
        Log.info("根据公寓Id查询公寓内楼层水表数,apartmentId：{}", apartmentId);
        return watermeterDao.getFloorWithWater(apartmentId);
    }

    /**
     * 集中式：根据楼层Id查询楼层下房间水表列表
     * @param floorId 楼层Id
     * @return 房间列表内嵌水表列表
     */
    @Override
    public List<RoomSimpleVO> getRoomWithWater(int floorId){
        Log.info("查询集中式楼层下房间水表列表,floorId：{}",floorId);
        return watermeterDao.getRoomWithWater(floorId);
    }

    /**
     * 集中式：根据房间Id查询水表详情
     * @param roomId 房间Id
     * @return 水表详情列表
     */
    @Override
    public List<WaterDetailVO> getWaterInRoom(int roomId){
        Log.info("查询集中式水表详情,roomId：{}",roomId);
        return watermeterDao.getWaterInRoom(roomId);
    }

    /**
     * 查询房间详情
     * @param roomId 房间Id
     * @return 房间信息
     */
    @Override
    public RoomDetailVO getRoomDetail(int roomId){
        Log.info("查询房间详情,roomId：{}",roomId);
        return watermeterDao.getRoomDetail(roomId);
    }

    /**
     * 更新房间内冷热水单价
     * @param price 用水单价（分/吨）
     * @param roomId 房间Id
     * @param meterType 水表类型 1-冷 2-热
     * @return 结果
     */
    @Override
    public Boolean updateRoomPrice(int price, int roomId, int meterType) {
        Log.info("修改房间水价,price:{},roomId:{},meterType:{}", price, roomId, meterType);
        Integer flag = watermeterDao.updateRoomPrice(price, roomId, meterType);
        return flag > 0;
    }

    @Override
    public RoomAccountVO getRoomAmount(int roomId, int meterType) {
        Log.info("查询房间水费,roomId:{},meterType:{}", roomId, meterType);
        return watermeterDao.getRoomAmount(roomId, meterType);
    }

    @Override
    public int makeWaterZero(int roomId, int houseCatalog){
        return watermeterDao.makeWaterZero(roomId,houseCatalog);
    }

    @Override
    public SmartDeviceV2 getSmartDeviceV2(long deviceId){
        return watermeterDao.getSmartDeviceV2(deviceId);
    }

    @Override
    public List<SmartDeviceV2> getSmartDeviceV2List(int userId, String brand) {
        return watermeterDao.getSmartDeviceV2List(userId, brand);
    }

    @Override
    public List<SmartDeviceV2> getAllSmartDeviceV2List() {
        return watermeterDao.getAllSmartDeviceV2List();
    }

    @Override
    public int changeBalance(int roomId,int houseCatalog,int amount,String payChannel,String action, String actionId) {
        SmartWatermeterAccount account = watermeterDao.getSmartWatermeterAccount(roomId, houseCatalog);
        SmartWatermeterAccountLog accountLog = new SmartWatermeterAccountLog();
        if (account == null) {
            account = new SmartWatermeterAccount();
            account.setBalance(amount);
            account.setHouseCatalog(houseCatalog);
            account.setRoomId(roomId);
            watermeterDao.addSmartWatermeterAccount(account);
        } else {
            accountLog.setBalanceBefore(account.getBalance());
            account.setBalance(account.getBalance() + amount);
            watermeterDao.updateSmartWatermeterAccount(account);
        }
        accountLog.setBalanceAfter(accountLog.getBalanceBefore() + amount);
        accountLog.setAction(action);
        accountLog.setActionId(actionId);
        accountLog.setPayChannel(payChannel);
        accountLog.setAmount(amount);
        accountLog.setHouseCatalog(houseCatalog);
        accountLog.setRoomId(roomId);
        return watermeterDao.addSmartWatermeterAccountLog(accountLog);
    }

    @Override
    public List<RecordVO> getRecordList(int watermeterId, String startTime, String endTime) {
        List<SmartWatermeterRecord> smartWatermeterRecords = findWatermeterRecordByWatermeterIdAndTime(watermeterId,startTime,endTime);
        WatermeterVO watermeter = getWatermeterById(watermeterId);

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,RecordVO> recordMap = new HashMap<>();
        for(SmartWatermeterRecord item : smartWatermeterRecords) {
            String date = sdf.format(item.getCreatedAt());
            if (recordMap.containsKey(date)) {
                if (item.getDeviceAmount() > recordMap.get(date).getLast()) {
                    recordMap.get(date).setLast(item.getDeviceAmount());
                }
                if (item.getDeviceAmount() < recordMap.get(date).getInitial()) {
                    recordMap.get(date).setInitial(item.getDeviceAmount());
                }
            } else {
                RecordVO record = new RecordVO();
                record.setDate(date);
                record.setLast(item.getDeviceAmount());
                record.setInitial(item.getDeviceAmount());
                recordMap.put(date, record);
            }
        }
        List<RecordVO> recordList = new ArrayList<>(recordMap.values());
        recordList.sort(Comparator.comparing(RecordVO::getDate));
        for (int i = 1;i<recordList.size();i++) {
            recordList.get(i).setInitial(recordList.get(i - 1).getLast());
        }
        for(RecordVO record : recordList){
            record.setUsed(record.getLast() - record.getInitial());
            record.setAmount(record.getUsed() * watermeter.getPrice() / 100.0);
        }
        return recordList;
    }
}
