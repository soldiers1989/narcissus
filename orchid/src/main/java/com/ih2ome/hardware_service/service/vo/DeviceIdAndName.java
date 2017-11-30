package com.ih2ome.hardware_service.service.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/30
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class DeviceIdAndName implements Serializable {
    private String id;
    private String Name;
    private String serialId;
    private List<DeviceIdAndName>deviceIdAndNames;
}
