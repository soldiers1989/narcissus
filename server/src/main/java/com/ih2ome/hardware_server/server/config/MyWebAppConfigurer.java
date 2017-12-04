package com.ih2ome.hardware_server.server.config;

import com.ih2ome.hardware_server.server.interceptor.OperateLogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/23
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Configuration
public class MyWebAppConfigurer  extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new OperateLogInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
