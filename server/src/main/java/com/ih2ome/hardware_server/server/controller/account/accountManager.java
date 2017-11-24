package com.ih2ome.hardware_server.server.controller.account;

import com.ih2home.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import org.springframework.web.bind.annotation.*;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/24
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@RestController
@RequestMapping("/accountManager")
public class accountManager extends BaseController {

    /**
     * 账号列表
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/account/{apiRequestVO}",method = RequestMethod.GET,produces = {"application/json"})
    public String accountList(@PathVariable String apiRequestVO){
        return "";
    }

    @RequestMapping(value="/account",method = RequestMethod.POST,produces = {"application/json"})
    public String addAccount(@RequestBody ApiRequestVO apiRequestVO){
        return "";
    }

    @RequestMapping(value="/account",method = RequestMethod.PUT,produces = {"application/json"})
    public String updateAccount(@RequestBody ApiRequestVO apiRequestVO){
        return "";
    }

    @RequestMapping(value="/account",method = RequestMethod.DELETE,produces = {"application/json"})
    public String deleteAccount(@RequestBody ApiRequestVO apiRequestVO){
        return "";
    }

    @RequestMapping(value="/accountRole",method = RequestMethod.GET,produces = {"application/json"})
    public String accountRole(@PathVariable String apiRequestVO){
        return "";
    }

}
