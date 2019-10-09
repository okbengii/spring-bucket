package com.beng.service.impl;

import java.io.UnsupportedEncodingException;

import javax.jws.WebService;

import com.alibaba.fastjson.JSONObject;
import com.beng.model.User;
import com.beng.service.IService;

/**
 * description: 对外接口-方法实现
 * 
 * @date 2019年10月9日
 */
@WebService(name = "UserService", targetNamespace = "http://service.beng.com/", endpointInterface = "com.beng.service.IService")
public class UserServiceImpl implements IService {

    @Override
    public String getUserName(String id) throws UnsupportedEncodingException {
        System.out.print("=========>>>>>>>>>" + id);
        // TODO 调用数据库（查询数据库业务逻辑）

        User user = User.builder().id(1).username("小机灵鬼").age(18).build();
        return JSONObject.toJSONString(user);
    }

    @Override
    public User getUserNameById(String id) throws UnsupportedEncodingException {
        System.out.print("=========>>>>>>>>>" + id);
        // TODO 调用数据库（查询数据库业务逻辑）

        return User.builder().id(1).username("小机灵鬼").age(18).build();
    }

    @Override
    public User getUser(String id) throws UnsupportedEncodingException {
        System.out.print("=========>>>>>>>>>" + id);
        // TODO 调用数据库（查询数据库业务逻辑）

        return User.builder().id(1).username("小机灵鬼").age(18).build();
    }

}
