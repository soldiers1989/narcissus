package com.ih2home.hardware_service.service.model;


import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author Lucius
 * create by 2017/10/30
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class User implements Serializable{

	private String id;
	
	private String username;
	
	private String password;

	private String gender;

	private Integer age;

}
