package com.ih2ome.hardware_server.server.controller.hardware;

import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/24
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@RestController
@RequestMapping("/ammeter")
public class ammeterController extends BaseController {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    /**
     * 集中式房源list
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/concentratedList/{apiRequestVO}",method = RequestMethod.GET,produces = {"application/json"})
    public String concentratedList(@PathVariable String apiRequestVO){
        ApiRequestVO apiRequestVOObjct = (ApiRequestVO)getDataObject(apiRequestVO,ApiRequestVO.class);

        return "";
    }

    /**
     * 分散式房源list
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/dispersedList/{apiRequestVO}",method = RequestMethod.GET,produces = {"application/json"})
    public String dispersedList(@PathVariable String apiRequestVO){
        return "";
    }

    /**
     * 查看电表关系
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/ammeterRelation/{apiRequestVO}",method = RequestMethod.GET,produces = {"application/json"})
    public String ammeterRelation(@PathVariable String apiRequestVO){
        return "";
    }

    /**
     * 电表信息查询
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/ammeterInfo/{apiRequestVO}",method = RequestMethod.GET,produces = {"application/json"})
    public String ammeterInfo(@PathVariable String apiRequestVO){
        return "";
    }

    /**
     * 使用场景变更
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/scenarioUpdate",method = RequestMethod.PUT,produces = {"application/json"})
    public String scenarioUpdate(@RequestBody ApiRequestVO apiRequestVO){
        return "";
    }

    /**
     * 付费模式变更
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/payMatureUpdate",method = RequestMethod.PUT,produces = {"application/json"})
    public String payMatureUpdate(@RequestBody ApiRequestVO apiRequestVO){
        return "";
    }

    /**
     * 修改电费单价
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/unitPriceOfElectricityUpdate",method = RequestMethod.PUT,produces = {"application/json"})
    public String unitPriceOfElectricityUpdate(@RequestBody ApiRequestVO apiRequestVO){
        return "";
    }

    /**
     * 电表通断电操作
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/operateAmmeter",method = RequestMethod.POST,produces = {"application/json"})
    public String operateAmmeter(@RequestBody ApiRequestVO apiRequestVO){
        return "";
    }


}
