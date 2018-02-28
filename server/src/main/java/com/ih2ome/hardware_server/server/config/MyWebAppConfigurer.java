package com.ih2ome.hardware_server.server.config;

import com.ih2ome.hardware_server.server.interceptor.OperateLogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/23
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Configuration
public class MyWebAppConfigurer  extends WebMvcConfigurerAdapter {

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new OperateLogInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }





//    @Bean("ssoValidateFilter")
//    public Filter ssoValidateFilter() {
//        return new SsoValidateFilter();
//    }
//
//    /**
//     * token过滤器
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean ssoValidateFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(ssoValidateFilter());
//        registration.addUrlPatterns("/mannager/*","/watermeter/manager/*","/ammeter/*");
//        registration.addInitParameter("notFilterDir","user/login/login");
//        registration.setName("ssoValidateFilter");
//        return registration;
//    }
}
