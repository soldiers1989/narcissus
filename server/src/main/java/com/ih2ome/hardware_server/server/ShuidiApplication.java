package com.ih2ome.hardware_server.server;

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
@ComponentScan(basePackages = {"com.ih2ome.hardware_service.service","com.ih2ome.hardware_server.server"})
public class ShuidiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShuidiApplication.class, args);
	}
}
