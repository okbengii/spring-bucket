package com.beng.service;

import java.io.UnsupportedEncodingException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.beng.model.User;

/**
 * description: 定义一个对外服务的接口
 * 
 * @date 2019年10月9日
 */
@WebService(targetNamespace = "http://service.beng.com/")
public interface IService {

    @WebMethod
    String getUserName(@WebParam(name = "id") String id) throws UnsupportedEncodingException;

    public User getUserNameById(String id) throws UnsupportedEncodingException;

    @WebMethod
    public User getUser(String id) throws UnsupportedEncodingException;
}
