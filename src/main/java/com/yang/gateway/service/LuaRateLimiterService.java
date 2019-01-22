package com.yang.gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jevon
 */

@Service(value = "luaRateLimiterService")
public class LuaRateLimiterService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private DefaultRedisScript<Long> getRedisScript;

    @PostConstruct
    public void init(){
        getRedisScript = new DefaultRedisScript<Long>();
        getRedisScript.setResultType(Long.class);
        getRedisScript.setLocation(new ClassPathResource("scripts/my_rate_limiter.lua"));
    }

//    public List<Long> exec(String ip) {
//        List<String> keys = new ArrayList<>();
//        keys.add(ip);
//        keys.add(System.currentTimeMillis()+"");
//        return redisTemplate.execute(getRedisScript, keys, "10", "10", System.currentTimeMillis()+"", "1");
//    }


    /**
     * @param key 标识
     * @param maxPermits 桶最大token数
     * @param rate 添加token速度
     * @return 成功返回 1, String app
     */
    public Long initRateLimiter(String key, String maxPermits, String rate) {
        List<String> keys = new ArrayList<>();
        keys.add(key);
        return redisTemplate.execute(getRedisScript, keys, "init", maxPermits, rate);
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
        List<String> keys = new ArrayList<>();
        keys.add(key);
        return redisTemplate.execute(getRedisScript, keys, "acquire", maxPermits, rate, permits, currentMillSecond);
    }

    /**
     * @param key 标识
     * @return 成功返回1
     */
    public Long delete(String key) {
        List<String> keys = new ArrayList<>();
        keys.add(key);
        return redisTemplate.execute(getRedisScript, keys, "delete");
    }

}
