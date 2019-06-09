package com.beng.config;

import org.redisson.Redisson;
import org.redisson.config.Config;

/**
 * Redis 分布式锁 Redisson
 * 
 * @author apple
 */
public class RedissonManager {

    private RedissonManager() {

    }

    private static Config config = new Config();
    private static Redisson redisson = null;
    static {
        config.useSingleServer().setAddress("127.0.0.1:6379");
        // 得到redisson对象
        redisson = (Redisson) Redisson.create(config);
    }

    // 获取redisson对象的方法
    public static Redisson getRedisson() {
        return redisson;
    }
}
