package com.beng.utils;

import redis.clients.jedis.Jedis;

/**
 * jedis 操作 redis
 * 
 * @author apple
 */
// 使用 Redis 一定要设置过期时间!!!
// 使用 Redis 一定要设置过期时间!!!
// 使用 Redis 一定要设置过期时间!!!
// 重要的事情说三遍!!!
public class RedisUtils {

    private static Jedis jedis;
    static {
        jedis = new Jedis("localhost", 6379);
        // jedis.auth("123456");
    }

    public static Jedis getJedisConn() {
        return jedis;
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public static String set(String key, String value) {
        return jedis.set(key, value);
    }

    /**
     * @param key
     * @param value
     * @param time
     * @return
     */
    public static String setExpire(String key, String value, long time) {
        return jedis.set(key, value, "NX", "EX", time);
    }

    /**
     * @param key
     * @return
     */
    public static Long incr(String key) {
        return jedis.incr(key);
    }

    /**
     * 根据 key 获取 value
     * 
     * @param key
     * @return
     */
    public static String get(String key) {
        return jedis.get(key);
    }

    public static void main(String[] args) {
        System.out.println("--------------------------  测试 String -------------------------------");
        testString();

        System.out.println("--------------------------  测试 List -------------------------------");
        testList();

        System.out.println("--------------------------  测试 Set -------------------------------");
        testSet();

        System.out.println("--------------------------  测试 Hash -------------------------------");
        testHash();

        System.out.println("--------------------------  测试 Zset -------------------------------");
        testZset();

    }

    private static void testHash() {
        // 添加数据
        jedis.hset("hkey1", "fkey1", "1");
        jedis.hset("hkey1", "fkey2", "2");
        jedis.hset("hkey1", "fkey3", "3");
        System.out.println(jedis.hget("hkey1", "fkey2"));
        System.out.println(jedis.hgetAll("hkey1"));
        // 删除
        jedis.hdel("hkey1", "fkey2");
        System.out.println(jedis.hget("hkey1", "fkey2"));
        System.out.println(jedis.hgetAll("hkey1"));
    }

    private static void testZset() {
        // 添加数据
        jedis.zadd("zkey1", 1, "zfkey1");
        jedis.zadd("zkey1", 3, "zfkey3");
        jedis.zadd("zkey1", 2, "zfkey2");
        jedis.zadd("zkey1", 5, "zfkey5");
        jedis.zadd("zkey1", 4, "zfkey4");
        // 获取 set 中的 所有元素
        System.out.println(jedis.zrange("zkey1", 0, -1));
        // 按照分数获取 set 中的元素
        System.out.println(jedis.zrangeByScore("zkey1", 0, 2));
        jedis.zrem("zkey1", "zfkey3");
        System.out.println(jedis.zrange("zkey1", 0, -1));
    }

    private static void testSet() {
        jedis.del("skey1");
        // 向 set 中添加元素
        jedis.sadd("skey1", "6", "2", "3", "4", "2", "6");
        System.out.println(jedis.smembers("skey1"));
        // 检查元素是否存在
        System.out.println(jedis.sismember("skey1", "3"));
        System.out.println(jedis.sismember("skey1", "9"));
        // 移除元素
        jedis.srem("skey1", "2");
        System.out.println(jedis.sismember("skey1", "2"));
    }

    public static void testList() {
        jedis.del("lkey1");
        // 集合添加元素
        jedis.lpush("lkey1", "1", "2", "3", "4");
        // 按索引获取值（注意是倒序）
        System.out.println("list:" + jedis.lindex("lkey1", 0));
        // 获取集合的长度
        System.out.println(jedis.llen("lkey1"));
        // 弹出集合中的元素，弹出后集合中不在包含该元素
        String pop = jedis.lpop("lkey1");
        while (pop != null) {
            System.out.print(pop);
            System.out.print(" ");
            pop = jedis.lpop("lkey1");
        }
        System.out.println();

    }

    public static void testString() {
        // 设置 String 类型
        jedis.set("test", "hello");
        System.out.println(jedis.get("test"));
        // 在字符串后面直接添加
        jedis.append("test", " world .");
        System.out.println(jedis.get("test"));
        // 删除操作
        jedis.del("test");
        System.out.println(jedis.get("test"));
        // 测试一下 NX | XX
        // NX|XX, NX -- Only set the key if it does not already exist. XX --
        // Only set the key
        // if it already exist.
        jedis.set("test", "hello world", "NX", "EX", 1000);
        System.out.println(jedis.get("test"));
        jedis.set("test", "hello world!", "XX", "EX", 1000);
        System.out.println(jedis.get("test"));
        // 批量添加
        jedis.mset("test1", "value1", "test2", "value2");
        System.out.println(jedis.get("test1"));
        System.out.println(jedis.get("test2"));
        // incr 使用 可以在计数器时使用
        jedis.set("test:num", "1");
        System.out.println(jedis.get("test:num"));
        jedis.incr("test:num");
        System.out.println(jedis.get("test:num"));
        // 按步长累计
        jedis.incrBy("test:num", 5);
        System.out.println(jedis.get("test:num"));
    }
}
