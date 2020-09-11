package com.xp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
//跨域请求
@Configuration
public class GlobalCorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        //1.添加CORS配置信息
        CorsConfiguration config=new CorsConfiguration();
        //2.允许的域地址，写成*可能会导致cookie无法使用
        config.addAllowedOrigin("*");
        //3.是否发送Cookie信息
        config.setAllowCredentials(true);
        //4.允许的请求方式
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        //5.允许的头信息
        config.addAllowedHeader("*");
        //有效时长
        config.setMaxAge(3600L);
        //6.添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource configurationSource=new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",config);
        //返回一个新的CorsFilter
        return new CorsFilter(configurationSource);
    }
}
