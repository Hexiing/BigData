package com.example.redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class JedisTest {
    public static void main(String[] args) {
        Jedis jd = new Jedis("192.168.10.102",6379);
        Set<String> keys = jd.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
    }
}
