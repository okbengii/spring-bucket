package com.beng.service;

import java.io.UnsupportedEncodingException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.beng.model.User;

@WebService
public interface IService {
    @WebMethod
    String getUserName(@WebParam(name = "id") String id) throws UnsupportedEncodingException;

    public User getUserNameById(String id) throws UnsupportedEncodingException;

    @WebMethod
    public User getUser(String id) throws UnsupportedEncodingException;
}
