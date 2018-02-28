package com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding;

import lombok.Data;

/**
 * @author Sky
 * @create 2018/01/18
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class YunDingRoomInfoVO {
    //公寓id
    private String homeId;
    //房间id
    private String roomId;
    //房间别名
    private String roomName;
    //房间描述
    private String roomDescription;
    //1.未安装，2.可管理
    private String spState;
    //3.已分配安装，4.未分配安装，5.已完成安装
    private String installState;

}
