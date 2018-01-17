package com.ih2ome.sunflower.vo.thirdVo.watermeter;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class YunDingResponseVo {
    private int ErrNo;//错误号
    private String ErrMsg;//错误描述
    private JSONObject result;//成功返回

}
