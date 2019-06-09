package com.beng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beng.lock.DistributedRedisLock;

@Controller
@RequestMapping(value = "/")
public class HelloController {

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        String key = "test123";
        // 加锁
        DistributedRedisLock.acquire(key);
        // 处理逻辑
        // 释放锁
        DistributedRedisLock.release(key);
        return "success";
    }

}
