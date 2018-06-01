package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.sunflower.entity.narcissus.*;
import com.ih2ome.sunflower.model.backup.HomeVO;
import com.ih2ome.sunflower.model.backup.SaasSmartLock;
import com.ih2ome.sunflower.vo.pageVo.smartLock.*;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockPasswordVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * @author Sky
 * @create 2018/01/22
 * @email sky.li@ixiaoshuidi.com
 **/
@Repository
public interface SmartLockDao {
    /**
     * 根据用户id查询该用户下的分散式房源信息
     *
     * @param userId
     * @return
     */
    List<HomeVO> findDispersedHomes(String userId);

    /**
     * 根据房源id查询该用户下的分散式房源信息
     * @param houseId
     * @return
     */
    List<HomeVO> findDispersedSubaccountHomes(String houseId);

    /**
     * 根据用户id查询该用户下的集中式房源信息
     * @param userId
     * @return
     */
    List<HomeVO> findConcentrateHomes(String userId);

    /**
     * 根据房源id查询该用户下的集中式房源信息
     * @param apartmentId
     * @return
     */
    List<HomeVO> findCentralizedHomes(String apartmentId);

    /**
     * 取消关联房源(分散式，集中式)
     *
     * @param smartHouseMappingVO
     */
    void cancelAssociation(SmartHouseMappingVO smartHouseMappingVO);

    /**
     * 查询房源映射表中的记录
     *
     * @param houseMapping
     * @return
     */
    SmartHouseMappingVO findHouseMappingRecord(SmartHouseMappingVO houseMapping);

    /**
     * 新增房间关联记录
     *
     * @param houseMapping
     */
    void addAssociation(SmartHouseMappingVO houseMapping);

    /**
     * 修改房间关联记录
     *
     * @param houseMapping
     */
    void updateAssociation(SmartHouseMappingVO houseMapping);

    /**
     * 根据公共区域，查询该房源下的所有关联记录(分散式)
     *
     * @param houseMapping
     * @return
     */
    List<SmartHouseMappingVO> findDispersedRoomMappingByPublicZone(SmartHouseMappingVO houseMapping);

    /**
     * 根据公共区域，查询该房源下的所有关联记录(集中式)
     *
     * @param houseMapping
     * @return
     */
    List<SmartHouseMappingVO> findConcentrateRoomMappingByPublicZone(SmartHouseMappingVO houseMapping);

    /**
     * 根据房间ID查询该房间所属房源的公共区域Id(分散式)
     *
     * @param roomId
     * @return
     */
    String findDispersedPublicZoneByRoomId(String roomId);

    /**
     * 根据房间ID查询该房间所属房源的公共区域Id(集中式)
     *
     * @param roomId
     * @return
     */
    String findConcentratePublicZoneByRoomId(String roomId);

    /**
     * 通过roomId清除该房间下的设备
     *
     * @param type
     * @param roomId
     */
    void clearDevicesByRoomId(@Param("type") String type, @Param("roomId") String roomId, @Param("providerCode") String providerCode);


    /**
     * 通过publicZoneId清除该公共区域下的设备
     *
     * @param type
     * @param publicZoneId
     */
    void clearDevicesByPublicZoneId(@Param("type") String type, @Param("publicZoneId") String publicZoneId, @Param("providerCode") String providerCode);

    /**
     * 新增智能设备记录
     *
     * @param gatewayDevice
     * @return
     */
    void addSmartDevice(SmartDeviceV2 gatewayDevice);

    /**
     * 新增智能网关记录
     *
     * @param smartGatewayV2
     */
    void addSmartGateway(SmartGatewayV2 smartGatewayV2);

    /**
     * 新增智能门锁记录
     *
     * @param publicLock
     */
    void addSmartLock(SmartLock publicLock);

    /**
     * 新增设备绑定网关记录
     *
     * @param lockDeviceId
     * @param gatewayDeviceId
     */
    void addSmartDeviceBind(@Param("lockDeviceId") String lockDeviceId, @Param("gatewayDeviceId") String gatewayDeviceId);

    /**
     * 新增门锁密码记录
     *
     * @param smartLockPassword
     */
    void addSmartLockPassword(SmartLockPassword smartLockPassword);

    /**
     * 根据门锁Id查询密码列表
     *
     * @param lockId
     * @return
     */
    List<SmartLockPassword> findPasswordListByLockId(@Param("lockId") String lockId);

    /**
     * 根据门锁id查询对应第三方门锁的uuid和厂商
     *
     * @param smartLockId
     * @return
     */
    SmartDeviceV2 findThirdLockUuid(@Param("smartLockId") String smartLockId);

    /**
     * 新增密码记录
     *
     * @param lockPasswordVo
     */
    void addLockPassword(LockPasswordVo lockPasswordVo);

    /**
     * 根据门锁id和密码编号查询该管理密码是否存在
     *
     * @param smartLockId
     * @param passwordId
     * @return
     */
    String findLockManagePassword(@Param("smartLockId") String smartLockId, @Param("passwordId") String passwordId);

    /**
     * 修改门锁密码
     *
     * @param lockPasswordVo
     */
    void updateLockPassword(LockPasswordVo lockPasswordVo);

    /**
     * 根据密码id查询密码的详细信息
     *
     * @param passwordId
     * @return
     */
    SmartLockPassword findPasswordById(@Param("passwordId") String passwordId);

    /**
     * 根据密码Id删除门锁密码
     *
     * @param passwordId
     */
    void deleteLockPassword(@Param("passwordId") String passwordId);

    /**
     * 冻结门锁密码
     *
     * @param passwordId
     */
    void frozenLockPassword(@Param("passwordId") String passwordId);

    /**
     * 解冻门锁密码
     *
     * @param passwordId
     */
    void unFrozenLockPassword(@Param("passwordId") String passwordId);

    /**
     * 查询门锁详情
     *
     * @param lockId
     * @return
     */
    SmartLockDetailVO findSmartLockDetail(@Param("lockId") String lockId);

    /**
     * 通过公共区域id查询房源信息(集中式)
     *
     * @param publicZoneId
     * @return
     */
    SmartLockDetailVO findConcentrateHomeInfoByPublicZoneId(@Param("publicZoneId") String publicZoneId);

    /**
     * 通过房间id查询房源信息(集中式)
     *
     * @param roomId
     * @return
     */
    SmartLockDetailVO findConcentrateHomeInfoByRoomId(@Param("roomId") String roomId);

    /**
     * 通过公共区域id查询房源信息(分散式)
     *
     * @param publicZoneId
     * @return
     */
    SmartLockDetailVO findDispersedHomeInfoByPublicZoneId(@Param("publicZoneId") String publicZoneId);

    /**
     * 通过房间id查询房屋信息(分散式)
     *
     * @param roomId
     * @return
     */
    SmartLockDetailVO findDispersedHomeInfoByRoomId(@Param("roomId") String roomId);

    /**
     * 根据uuid删除门锁密码
     *
     * @param uuid
     */
    void deleteLockPasswordByUuid(String uuid);

    /**
     * 查询开门记录
     *
     * @param lockId
     * @return
     */
    List<SmartMistakeInfo> findOpenLockRecord(String lockId);

    /**
     * 查询操作记录
     *
     * @param lockId
     * @return
     */
    List<SmartMistakeInfo> findHistoryOperations(String lockId);

    /**
     * 查询异常记录
     *
     * @param lockId
     * @return
     */
    List<SmartMistakeInfo> findExceptionRecords(String lockId);

    /**
     * 更新电量
     *
     * @param lockInfoVo
     */
    void updateBatteryInfo(LockInfoVo lockInfoVo);

    /**
     * 新增操作日志
     *
     * @param smartLockLog
     */
    void addSmartLockOperationLog(SmartLockLog smartLockLog);

    /**
     * 根据uuid更新密码
     *
     * @param lockPasswordVo
     */
    void updateLockPasswordByUuid(LockPasswordVo lockPasswordVo);

    /**
     * 解绑门锁
     *
     * @param uuid
     */
    void deleteSmartLockByUuid(String uuid);

    /**
     * 查询该公区下绑定的内门锁
     *
     * @param type
     * @param publicZoneId
     * @param providerCode
     * @return
     */
    List<SmartLockGateWayHadBindInnerLockVO> findGatewayBindInnerLock
    (@Param("type") String type, @Param("publicZoneId") String publicZoneId, @Param("providerCode") String providerCode);

    /**
     * 修改内门锁的绑定关系
     *
     * @param smartGatewayId
     * @param gatewayId
     * @param innerLockId
     */
    void updateInnerLockBindGateway
    (@Param("smartGatewayId") String smartGatewayId, @Param("gatewayId") Long gatewayId, @Param("innerLockId") Long innerLockId);

    /**
     * 根据分散式房源id添加公共区域
     * @param roomId
     */
    void dispersiveAddition(String roomId);

    /**
     *  根据用户id查询该用户下的分散式房源信息中没有公共区域的房源id
     * @param userId
     * @return
     */
    List<String> findDispersedHomesAndPublicZone(String userId);

    /**
     *  根据子帐号id查询该用户下的分散式房源信息中没有公共区域的房源id
     * @param employerId
     * @return
     */
    List<String> queryDispersedHomesAndPublicZone(String employerId);

    /**
     * 根据用户id查询该用户下的集中式房源信息中没有公共区域的房源id
     * @param userId
     * @return
     */
    List<String> centralizedFindDispersedHomes(String userId);

    /**
     * 根据子账号id查询该用户下的集中式房源信息中没有公共区域的房源id
     * @param employerapatmentsid
     * @return
     */
    List<String> centralizedqueryDispersedHomes(String employerapatmentsid);

    /**
     * 根据集中式房源id添加公共区域
     * @param roomId
     */
    void centralizedAddition(String roomId);

    /**
     * 根据id查询分散式子账号信息
     * @param userId
     * @return
     */
    String queryEmployer(String userId);

    /**
     * 查询分散式子账号可控房源id
     * @param employerId
     * @return
     */
    List<String> queryEmployerHouses(String employerId);

    /**
     * 根据id查询集中式子账号信息
     * @param userId
     * @return
     */
    String findEmployer(String userId);

    /**
     * 查询集中式子账号可控房源id
     * @param employerapatmentsid
     * @return
     */
    List<String> findEmployerApatments(String employerapatmentsid);


    /**
     * 集中式根据roomid查看门锁编码
     * @param roomId
     * @return
     */
    SaasSmartLock findSmartLock(String roomId);

    /**
     * 分散式根据roomid查看门锁编码
     * @param roomId
     * @return
     */
    SaasSmartLock findSmartLockCode(String roomId);

    /**
     * 集中式查询门锁总数
     * @param userId
     * @return
     */
    String findSmartLockCount(String userId);

    /**
     * 集中式查询网关总数
     * @param userId
     * @return
     */
    String findSmartGateWayCount(String userId);

    /**
     * 分散式查询门锁总数
     * @param userId
     * @return
     */
    String QuerySmartLockCount(String userId);

    /**
     * 分散式查询网关总数
     * @param userId
     * @return
     */
    String querySmartGatWayCount(String userId);

    List<String> findUserId(String userId);

    /**
     * 新增门锁网关记录
     * @param smartGatewayV2
     */
    void  saveGatWay(SmartGatewayV2 smartGatewayV2);

    /**
     * 新增水表记录
     * @param smartWatermeter
     */
    void  saveWaterMeter(SmartWatermeter smartWatermeter);

    /**
     * 根据公共区查device_ID
     * @param gateWayUuid
     * @return
     */
    String querySmartGatWayid( String gateWayUuid );


    String findUserIds(String userId);

    int updateAutoCollection(@Param("passwordId") int passwordId, @Param("autoCollection") int autoCollection);

    List<PasswordRoomVO> getPasswordRoomList();
    List<PasswordRoomVO> getFrozenPassword(@Param("roomId") int roomId,@Param("houseCatalog") int houseCatalog);

    com.ih2ome.sunflower.entity.caspain.RoomContract getCaspainRoomContract(String roomId);
    com.ih2ome.sunflower.entity.caspain.RoomRentorder getCaspainRoomRentorder(@Param("contractId") long contractId, @Param("roomRentOrderId") int roomRentOrderId);
    RoomCompanyVO getCaspainRoomCompany(long roomId);
    com.ih2ome.sunflower.entity.volga.RoomContract getVolgaRoomContract(String roomId);
    com.ih2ome.sunflower.entity.volga.RoomRentorder getVolgaRoomRentorder(@Param("contractId") long contractId, @Param("roomRentOrderId") int roomRentOrderId);
    RoomCompanyVO getVolgaRoomCompany(long roomId);
}
