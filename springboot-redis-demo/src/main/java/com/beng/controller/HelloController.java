package com.beng.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beng.lock.DistributedRedisLock;
import com.beng.utils.RedisUtil;

@Controller
@RequestMapping(value = "/")
public class HelloController {

    private static AtomicInteger count = new AtomicInteger(0);
    private static AtomicInteger count1 = new AtomicInteger(0);

    @Autowired
    RedisUtil redisUtil;

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() throws InterruptedException {
        String key = "test_num";
        int num = count.incrementAndGet();
        // 加锁
        DistributedRedisLock.acquire(key);
        // 处理逻辑
        redisUtil.incr("test_num");
        // Thread.sleep(1000);
        System.out.println("===========第 " + num + " 次请求===================" + redisUtil.get("test_num"));
        // 释放锁
        DistributedRedisLock.release(key);
        return "success";
    }

    @RequestMapping(value = "hello1", method = RequestMethod.GET)
    @ResponseBody
    public String hello1() throws InterruptedException {
        int num = count1.incrementAndGet();
        // 处理逻辑
        redisUtil.incr("test_num1");
        // Thread.sleep(1000);
        System.out.println("TEST1===========第 " + num + " 次请求===================" + redisUtil.get("test_num1"));
        return "success";
    }

}
