package com.nb.user.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    public String getUserName() {
        System.out.println("My name is Tom ...");
        return "My name is Tom";
    }
}
