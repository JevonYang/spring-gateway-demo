package com.yang.gateway.limiter;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jevon
 */

@Component
public class RateLimiterFactory {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

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
    public RedisRateLimiter build(String key, long permitsPerSecond, long maxBurstSeconds) {
        if (!rateLimiterMap.containsKey(key)) {
            synchronized (this) {
                if (!rateLimiterMap.containsKey(key)) {
                    RedisLocker redisLocker = new RedisLocker(redissonClient);
                    String requestId = UUID.randomUUID().toString();
                    rateLimiterMap.put(key, new RedisRateLimiter(key, maxBurstSeconds, permitsPerSecond, redisLocker, redisTemplate));
                }
            }
        }
        return rateLimiterMap.get(key);
    }
}
