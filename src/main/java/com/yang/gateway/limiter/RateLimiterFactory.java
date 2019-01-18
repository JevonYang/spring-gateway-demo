package com.yang.gateway.limiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jevon
 */

@Component
public class RateLimiterFactory {

    @Autowired
    private RedisLocker distributedLock;

    /**
     * 本地持有对象
     */
    private volatile Map<String, RedisRateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

    /**
     * @param key              redis key
     * @param permitsPerSecond 每秒产生的令牌数
     * @param maxBurstSeconds  最大存储多少秒的令牌
     * @return
     */
    public RedisRateLimiter build(String key, Double permitsPerSecond, Integer maxBurstSeconds, JedisCluster jedisCluster) {
        if (!rateLimiterMap.containsKey(key)) {
            synchronized (this) {
                if (!rateLimiterMap.containsKey(key)) {
                    String requestId = UUID.randomUUID().toString();
                    rateLimiterMap.put(key, new RedisRateLimiter(key, permitsPerSecond, maxBurstSeconds, distributedLock, requestId, jedisCluster));
                }
            }
        }
        return rateLimiterMap.get(key);
    }
}
