package com.ih2home.hardware_server.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author Lucius
 * create by 2017/10/30
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.ih2home.hardware_service.service","com.ih2home.hardware_server.server","com.ih2home.utils.common"})
public class ShuidiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShuidiApplication.class, args);
	}
}
