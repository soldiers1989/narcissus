package com.ih2ome.hardware_service.service.dao;

import java.util.List;

import com.ih2ome.hardware_service.service.model.User;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lucius
 * create by 2017/10/30
 * @Emial Lucius.li@ixiaoshuidi.com
 */

@Repository
public interface UserMapper {

	public List<User> getUser(User user);

	public int addUser(User user);

	public int updateUser(User user);

	public void deleteUser(User user);
}
