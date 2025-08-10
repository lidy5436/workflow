package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 */
@Configuration
public class CorsConfig {

    /**
     * 全局跨域过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        // 1. 创建CORS配置对象
        CorsConfiguration config = new CorsConfiguration();
        // 允许的源（域名），*表示允许所有源（生产环境建议指定具体域名）
        config.addAllowedOriginPattern("*");
        // 允许的请求头，*表示允许所有请求头
        config.addAllowedHeader("*");
        // 允许的请求方法，*表示允许所有HTTP方法（GET, POST, PUT, DELETE等）
        config.addAllowedMethod("*");
        // 是否允许携带Cookie（跨域请求时）
        config.setAllowCredentials(true);
        // 预检请求的有效期（秒），在此期间不需要重新发送预检请求
        config.setMaxAge(3600L);
        // 2. 配置URL映射，/**表示对所有接口生效
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // 3. 创建并返回CORS过滤器
        return new CorsFilter(source);
    }
}
