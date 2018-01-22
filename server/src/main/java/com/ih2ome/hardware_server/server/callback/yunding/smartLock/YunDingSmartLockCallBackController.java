package com.ih2ome.hardware_server.server.callback.yunding.smartLock;

import com.ih2ome.common.api.enums.ExpireTime;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.common.utils.CacheUtils;
import com.ih2ome.common.utils.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/18
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@RestController
@RequestMapping("/yunDing/callBack")
@CrossOrigin
public class YunDingSmartLockCallBackController extends BaseController{

    final static String TOKEN_YUNDING_USER_CODE = "token_yunding_user_code";

    @RequestMapping(value="/setOAuthCode")
    public String setOAuthCode(HttpServletRequest request){
        String code = request.getParameter("code");
        String userId = request.getParameter("state");
        //第三方回调传userId
        if(StringUtils.isNotBlank(userId)){
            //用户授权code存redis，有效期4分30秒（文档中为5分钟，防止边界）
            CacheUtils.set(userId+"_"+TOKEN_YUNDING_USER_CODE,code, ExpireTime.FIVE_MIN.getTime()-30);

        }
        return structureSuccessResponseVO(null,new Date().toString(),"授权成功");

    }

}
