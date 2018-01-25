package com.ih2ome.sunflower.vo.pageVo.smartLock;

import lombok.Data;

/**
 * @author Sky
 * @create 2018/01/24
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class SmartHouseMappingVO {
    //0是集中，1是分散
    private String houseCatalog;
    //    1:公寓 ,2:楼层,3:房屋,4:房间
    private String dataType;
    //水滴数据ID
    private String h2omeId;
    //H2:水滴，GJ:果加，YD:云丁
    private String providerCode;
    //第三方数据ID
    private String threeId;
    //关联状态
    private String status;

    private String userId;
    private String roomId;
    private String thirdRoomId;
    private String type;
    private String factoryType;

    public static SmartHouseMappingVO toH2ome(SmartHouseMappingVO smartHouseMappingVO) {
        SmartHouseMappingVO smartHouseMappingVO1 = new SmartHouseMappingVO();
        smartHouseMappingVO1.setHouseCatalog(smartHouseMappingVO.getType());
        smartHouseMappingVO1.setH2omeId(smartHouseMappingVO.getRoomId());
        smartHouseMappingVO1.setThreeId(smartHouseMappingVO.getThirdRoomId());
        smartHouseMappingVO1.setProviderCode(smartHouseMappingVO.getFactoryType());
        return smartHouseMappingVO1;
    }
}
