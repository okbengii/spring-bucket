package com.okbeng.model;

import java.io.Serializable;

/**
 * User（可以使用 lombok 简化）
 * 
 * @author apple
 */
public class User implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
