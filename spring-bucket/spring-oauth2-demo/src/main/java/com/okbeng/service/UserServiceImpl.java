package com.okbeng.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.okbeng.model.CustomUserDetails;
import com.okbeng.model.User;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        /*
         * 模拟数据库操作
         */
        User user = new User();
        user.setUsername("10086");
        user.setPassword("123456");
        return new CustomUserDetails(user);
    }

}
