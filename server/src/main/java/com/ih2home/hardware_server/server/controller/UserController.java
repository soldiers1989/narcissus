package com.ih2home.hardware_server.server.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ih2home.utils.common.CacheUtils;
import com.ih2home.utils.common.enums.ExpireTime;
import com.ih2home.hardware_service.service.model.User;
import com.ih2home.hardware_service.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *
 * @author Lucius
 * create by 2017/10/30
 * @Emial Lucius.li@ixiaoshuidi.com
 */

@RestController
@RequestMapping("/account")
public class UserController {

	@Autowired
	private UserService userService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private String responseJsonString(String code,String msg,Object data){
		JSONObject res = new JSONObject();
		res.put("code",code);
		res.put("msg",msg);
		res.put("data",JSON.toJSON(data));
		return res.toJSONString();
	}

	private String errorMsg(String msg){
		return  responseJsonString("1",msg,null);
	}

	private String successMsg(){
		return  responseJsonString("0","成功",null);
	}

	@RequestMapping(value="/user/{user}",method = RequestMethod.GET,produces = {"application/json"})
	public String userList(@PathVariable String user){
		User model  = JSONObject.parseObject(user,User.class);
		List <User> userList = userService.getUser(model);
		JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(userList));
		return responseJsonString("0","请求成功",jsonArray);

	}

	@RequestMapping(value = "/user",method = RequestMethod.POST,produces = {"application/json"})
	public String addUser(@RequestBody User user){
		userService.addUser(user);
		return successMsg();
	}

	@RequestMapping(value = "/user",method = RequestMethod.PUT,produces = {"application/json"})
	public String updateUser(@RequestBody User user){
		userService.updateUser(user);
		return successMsg();
	}

	@RequestMapping(value = "/user",method = RequestMethod.DELETE,produces = {"application/json"})
	public String deleteUser(@RequestBody User user){
		userService.deleteUser(user);
		return successMsg();
	}

	@RequestMapping(value="/redis",method = RequestMethod.POST,produces = "application/json")
	public String addRedisUser(@RequestBody User user){
		CacheUtils.set(user.getId(), user,ExpireTime.FIVE_MIN);
		return successMsg();
	}

	@RequestMapping(value="/redis/{id}",method = RequestMethod.GET,produces = "application/json")
	public String getRedisUser(@PathVariable String id){
		User user = CacheUtils.get(id,User.class);
		return responseJsonString("0","获取成功",user);
	}

	@RequestMapping(value="/redis/",method = RequestMethod.DELETE,produces = "application/json")
	public String deleteRedisUser(@RequestBody User user){
		CacheUtils.del(user.getId());
		return successMsg();
	}
}
