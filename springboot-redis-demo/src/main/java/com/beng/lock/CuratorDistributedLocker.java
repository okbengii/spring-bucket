package com.beng.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CuratorDistributedLocker {

    private static final String DEFAULT_LOCK_PATH = "/curator/lock/";

    @Autowired
    private CuratorFramework curatorFramework;

    public Locker getLocker(String lockKey) {
        return new Locker(lockKey, curatorFramework);
    }

    public static class Locker {

        private String lockKey;

        private InterProcessMutex mutex;

        private CuratorFramework curatorFramework;

        public Locker(String lockKey, CuratorFramework curatorFramework) {
            this.mutex = new InterProcessMutex(curatorFramework, DEFAULT_LOCK_PATH + lockKey);
        }

        public void lock() {
            try {
                this.mutex.acquire();
            } catch (Exception e) {
            }
        }

        public void unlock() {
            try {
                this.mutex.release();
            } catch (Exception e) {
            }
        }
    }

}
