package com.ih2ome.hardware_server.server.controller.mannager.account;

import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
public class AccountManager extends BaseController {

    /**
     * 账号列表
     * @param apiRequestVO
     * @return
     */
    @ApiOperation(value = "添加用户", httpMethod = "POST", response = String.class, notes = "add user")
    @ApiImplicitParam(name = "book", value = "图书详细实体", required = true, dataType = "Book")
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
