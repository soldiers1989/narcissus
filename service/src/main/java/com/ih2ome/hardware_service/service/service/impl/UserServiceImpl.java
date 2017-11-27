package com.ih2ome.hardware_service.service.service.impl;


import com.ih2ome.common.utils.ConstUtils;
import com.ih2ome.hardware_service.service.dao.UserMapper;
import com.ih2ome.hardware_service.service.model.User;
import com.ih2ome.hardware_service.service.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author Lucius
 * create by 2017/10/30
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Override
	public List<User> getUser(User user) {
		return userMapper.getUser(user);
	}

	@Override
	public void addUser(User user) {
		user.setPassword(ConstUtils.md5(user.getPassword()));
		userMapper.addUser(user);
	}

	@Override
	public void updateUser(User user) {
		if(user.getPassword()!=null){
			user.setPassword(ConstUtils.md5(user.getPassword()));
		}
		userMapper.updateUser(user);
	}

	@Override
	public void deleteUser(User user) {
		userMapper.deleteUser(user);
	}


}
