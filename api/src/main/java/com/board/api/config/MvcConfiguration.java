package com.board.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/boardWrite").setViewName("boardWrite");
        registry.addViewController("/boardDetail").setViewName("boardDetail");
        registry.addViewController("/boardModify").setViewName("boardModify");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/signUp").setViewName("signUp");
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() throws IOException {

        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        commonsMultipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024);
        return commonsMultipartResolver;
    }
}
