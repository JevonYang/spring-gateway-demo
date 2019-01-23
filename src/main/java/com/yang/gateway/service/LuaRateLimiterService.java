package com.yang.gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;

/**
 * @author jevon
 */

@Service(value = "luaRateLimiterService")
public class LuaRateLimiterService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private DefaultRedisScript<Long> getRedisScript;

    @PostConstruct
    public void init() {
        getRedisScript = new DefaultRedisScript<Long>();
        getRedisScript.setResultType(Long.class);
        getRedisScript.setLocation(new ClassPathResource("scripts/rate_limiter.lua"));
    }

    /**
     * @param key
     * @param maxPermits
     * @param rate
     * @param permits
     * @param currentMillSecond
     * @return 没有桶返回0 成功返回1 失败返回-1
     */
    public Long acquire(String key, String maxPermits, String rate, String permits, String currentMillSecond) {
        return redisTemplate.execute(getRedisScript, Collections.singletonList(key),maxPermits, rate, permits, currentMillSecond);
    }

}
