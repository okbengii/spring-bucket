package com.okbeng.model;

import java.util.Collections;

/**
 * 继承 security 的 User 类
 * 
 * @author apple
 */
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    private User user;

    public CustomUserDetails(User user) {
        super(user.getUsername(), user.getPassword(), true, true, true, true, Collections.EMPTY_SET);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
