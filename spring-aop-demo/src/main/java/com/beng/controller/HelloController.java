package com.beng.controller;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value="/")
@Slf4j
public class HelloController {

    @RequestMapping(value="/hello")
    @ResponseBody
    public String hello() {
        System.out.println("hello world!");
        try{
            int i = 1/0;
        }catch(Exception e){
            throw e;
        }

        return "hello world!";
    }
}