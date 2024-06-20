package com.serguzeo.StartSpring.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/404").setViewName("404");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/register/success").setViewName("register-success");
        registry.addViewController("/{path:^(?!home$|404$|login$|register$).*$}").setViewName("profile");
        registry.addViewController("/{username}/followers")
                .setViewName("userList");
        registry.addViewController("/{username}/following")
                .setViewName("userList");
    }

}
