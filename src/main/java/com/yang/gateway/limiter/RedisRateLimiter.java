package com.yang.gateway.limiter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author jevon
 */
public class RedisRateLimiter {

    private static Logger logger = LoggerFactory.getLogger(RedisRateLimiter.class);

    private StringRedisTemplate redisTemplate;

    private String key;

    private long maxPermits;

    private long rate;

    private RedisLocker redisLocker;

    public RedisRateLimiter(String key, long maxPermits, long rate, RedisLocker redisLocker, StringRedisTemplate redisTemplate) {
        this.key = key;
        this.maxPermits = maxPermits;
        this.rate = rate;
        this.redisLocker = redisLocker;
        this.redisTemplate = redisTemplate;
    }

    private void setPermits(String sKey, PermitsEntity.Permits permits) {
        // long ttl = (permits.getMaxPermits() / permits.getRate()) * 2;
        redisTemplate.opsForValue().set(sKey, permits.toByteArray().toString());
    }

    private void setPermits(String sKey, Permits permits) {
        // long ttl = (permits.getMaxPermits() / permits.getRate()) * 2;
        redisTemplate.opsForValue().set(sKey, JSONObject.toJSONString(permits));
    }

    private PermitsEntity.Permits getPermits(String sKey) {
        try {
            return PermitsEntity.Permits.parseFrom(redisTemplate.opsForValue().get(sKey).getBytes());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Permits getPermit(String sKey) {
        return JSON.parseObject(redisTemplate.opsForValue().get(sKey), Permits.class);
    }

    private PermitsEntity.Permits getPermit() {
        this.redisLocker.lock("Lock:" + this.key, 500, TimeUnit.MILLISECONDS);
        try {
            PermitsEntity.Permits permits = this.getPermits("RateLimiter:" + this.key);
            if (permits == null) {
                PermitsEntity.Permits.Builder builder = PermitsEntity.Permits.newBuilder();
                builder.setMaxPermits(this.maxPermits);
                builder.setRate(this.rate);
                builder.setCurrentPermits(this.maxPermits);
                builder.setLastMilliSecond(System.currentTimeMillis());
                permits = builder.build();
            }
            return permits;
        } finally {
            this.redisLocker.unLock("Lock:" + this.key);
        }
    }

    private Permits getDefaultPermits() {
        this.redisLocker.lock("Lock:" + this.key, 500, TimeUnit.MILLISECONDS);
        Permits permits = this.getPermit("RateLimiter:" + this.key);
        try {
            if (permits == null) {
                return new Permits(this.maxPermits, this.maxPermits, this.rate, System.currentTimeMillis());
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            this.redisLocker.unLock("Lock:" + this.key);
        }
        return permits;
    }

    public boolean tryAcquire(long queryPermits) {
        try {
            this.redisLocker.lock("Lock:" + key, 500L, TimeUnit.MILLISECONDS);
            Permits permits = this.getDefaultPermits();
            long fillTokens = (System.currentTimeMillis() - permits.getLastMilliSecond()) / (permits.getRate() * 1000);
            long currentPermits = permits.getCurrentPermits();
            if (fillTokens >= 1) {
                currentPermits += fillTokens;
            }
            if (currentPermits - queryPermits >= 0) {
                permits.setCurrentPermits(currentPermits - queryPermits);
                permits.setLastMilliSecond(System.currentTimeMillis());
                this.setPermits("RateLimiter:" + key, permits);
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.redisLocker.unLock("Lock:" + key);
        }
        return false;
    }

    public boolean tryAcquire() {
        return this.tryAcquire(1L);
    }

}
