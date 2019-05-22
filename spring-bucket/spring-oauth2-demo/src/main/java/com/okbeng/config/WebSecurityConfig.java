package com.okbeng.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

import com.okbeng.service.UserService;

/**
 * @desc spring security
 * @author apple
 */
@Configuration
public class WebSecurityConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);

    }
}
