package com.beng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beng.lock.CuratorDistributedLocker;
import com.beng.lock.CuratorDistributedLocker.Locker;

@RequestMapping
@RestController("/")
public class HelloV3Controller {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CuratorDistributedLocker curatorDistributedLocker;

    @RequestMapping("/delStockV3")
    public String delStock() {
        Locker locker = curatorDistributedLocker.getLocker("stock");
        try {
            locker.lock();
            Integer result = (Integer) redisTemplate.opsForValue().get("stock");
            if (result > 0) {
                int remainStock = result - 1;
                redisTemplate.opsForValue().set("stock", remainStock);
                System.out.println("Remain Stock: " + remainStock);
            } else {
                System.out.println("Remain Stcock: 0");
                // }
            }
        } catch (Exception e) {

        } finally {
            locker.unlock();
        }
        return "success";
    }

}
