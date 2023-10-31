package com.eauction.seller.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Component
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "PUT", "DELETE", "PUT")
                .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization")
                .exposedHeaders("Authorization");
//                .allowCredentials(true);
    }
}
