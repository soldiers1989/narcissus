package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <br>
 * TODO:name
 * @author Lucius
 * create by 2017/11/30
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class DeviceIdAndNameVo implements Serializable {
    private String id;
    private String name;
    private String serialId;
    private List<DeviceIdAndNameVo>deviceIdAndNames;
}
