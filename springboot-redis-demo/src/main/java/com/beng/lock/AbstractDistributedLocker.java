package com.beng.lock;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;

/**
 * 分布式锁接口
 * 
 * @author apple
 */
public interface AbstractDistributedLocker {

    RLock lock(String lockKey);

    RLock lock(String lockKey, long timeout);

    RLock lock(String lockKey, TimeUnit unit, long timeout);

    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);
}
