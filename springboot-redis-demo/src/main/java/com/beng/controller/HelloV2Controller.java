package com.beng.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HelloV2Controller {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private Redisson redisson;

    @RequestMapping("delStock")
    @ResponseBody
    public String delStock() {
        // synchronized (HelloV2Controller.class) { // 单体应用没有问题，但是在分布式情况下就不适用了
        String lockKey = "lockKey";
        // String clientId = UUID.randomUUID().toString();// 自己删除自己的锁
        RLock lock = redisson.getLock(lockKey);
        try {
            // Boolean lock = redisTemplate.opsForValue().setIfAbsent(lockKey,
            // "lockValue"); // 相当于jedis中的
            // jedis.setnx
            // Boolean lock = redisTemplate.opsForValue().setIfAbsent(lockKey,
            // clientId, 10, TimeUnit.SECONDS);
            // redisTemplate.expire(lockKey, 10, TimeUnit.SECONDS); // 不能保证原子性
            // if (!lock) {
            // return "false";
            // }
            lock.lock();
            // 业务逻辑代码
            // ....
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
            lock.unlock();
            // if (clientId.equals(redisTemplate.opsForValue().get(lockKey)))
            // // 加锁记得删除锁
            // redisTemplate.delete(lockKey);
        }
        return "success";
    }

}
