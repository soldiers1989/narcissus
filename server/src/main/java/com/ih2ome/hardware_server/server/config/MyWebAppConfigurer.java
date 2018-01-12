package com.ih2ome.hardware_server.server.config;

import com.ih2ome.hardware_server.server.filter.SsoValidateFilter;
import com.ih2ome.hardware_server.server.interceptor.OperateLogInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;

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





    @Bean("ssoValidateFilter")
    public Filter ssoValidateFilter() {
        return new SsoValidateFilter();
    }

    /**
     * token过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean ssoValidateFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(ssoValidateFilter());
        registration.addUrlPatterns("/mannager/*","/watermeter/manager/*","/ammeter/*");
        registration.addInitParameter("notFilterDir","user/login/login");
        registration.setName("ssoValidateFilter");
        return registration;
    }


//    @Bean("characterEncodingFilter")
//    public Filter characterEncodingFilter(){
//        return new CharacterEncodingFilter();
//    }

    /**
     * 字符编码过滤器
     * @return
     */
//    @Bean
//    public FilterRegistrationBean characterEncodingRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(characterEncodingFilter());
//        registration.addUrlPatterns("/*");
//        registration.addInitParameter("encoding", "UTF-8");
//        registration.addInitParameter("forceEncoding","true");
//        return registration;
//    }
}
