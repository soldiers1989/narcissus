package com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding;

import com.ih2ome.sunflower.model.backup.HomeVO;
import com.ih2ome.sunflower.model.backup.RoomVO;
import com.ih2ome.sunflower.vo.pageVo.enums.HouseMappingDataTypeEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sky
 * @create 2018/01/18
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class YunDingHomeInfoVO {
    //房源所属人
    private String userId;
    //房源id
    private String HomeId;
    //公寓类型(1.分散式 2.集中式)
    private String homeType;
    //国家
    private String country;
    //省份
    private String province;
    //城市
    private String city;
    //区域
    private String zone;
    //公寓具体信息
    private String location;
    //小区名
    private String block;
    //公寓名称
    private String homeName;
    //公寓的描述
    private String description;
    //公寓下的房间
    private List<YunDingRoomInfoVO> rooms;
    //公寓下的设备
    private List<YunDingDeviceInfoVO> devices;

    public static HomeVO toH2ome(YunDingHomeInfoVO yunDingHomeInfoVO) {
        HomeVO homeVO = new HomeVO();
        homeVO.setHomeId(yunDingHomeInfoVO.getHomeId());
        homeVO.setHomeName(yunDingHomeInfoVO.getHomeName());
        homeVO.setUserId(yunDingHomeInfoVO.getUserId());
        homeVO.setHomeType(yunDingHomeInfoVO.getHomeType());
        List<RoomVO> rooms = new ArrayList<RoomVO>();
        for (YunDingRoomInfoVO yunDingRoomInfoVO : yunDingHomeInfoVO.getRooms()) {
            RoomVO roomVO = new RoomVO();
            roomVO.setThirdHomeId(homeVO.getHomeId());
            roomVO.setThirdRoomId(yunDingRoomInfoVO.getRoomId());
            if ("default".equals(yunDingRoomInfoVO.getRoomName())) {
                roomVO.setThirdRoomName("公共区域");
                roomVO.setDataType(HouseMappingDataTypeEnum.PUBLICZONE.getCode());
            } else {
                roomVO.setThirdRoomName(yunDingRoomInfoVO.getRoomName());
                roomVO.setDataType(HouseMappingDataTypeEnum.ROOM.getCode());
            }
            for(YunDingDeviceInfoVO yunDingDeviceInfoVO:yunDingHomeInfoVO.getDevices()){
                if(yunDingDeviceInfoVO.getRoomId().equals(yunDingRoomInfoVO.getRoomId())){
                    roomVO.setUuid(yunDingDeviceInfoVO.getUuid());
                    roomVO.setManufactory(yunDingDeviceInfoVO.getManufactory());
                }
            }
            rooms.add(roomVO);
        }
        homeVO.setRooms(rooms);
        return homeVO;
    }
}
