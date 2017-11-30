package com.ih2ome.hardware_server.server.controller.mannager.operation;

import com.ih2ome.common.base.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/24
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@RestController
@RequestMapping("/operationLog")
public class operationLog extends BaseController {

    /**
     * 查看操作日志列表
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/operationLogList/{apiRequestVO}",method = RequestMethod.GET,produces = {"application/json"})
    public String operationLogList(@PathVariable String apiRequestVO){
        return "";
    }
}
