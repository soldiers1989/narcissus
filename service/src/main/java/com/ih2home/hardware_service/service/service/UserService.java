package com.ih2home.hardware_service.service.service;

import java.util.List;

import com.ih2home.hardware_service.service.model.User;
/**
 *
 * @author Lucius
 * create by 2017/10/30
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public interface UserService {

	public List<User> getUser(User user);

	public void addUser(User user);

	public void updateUser(User user);

	public void deleteUser(User user);
	
}
