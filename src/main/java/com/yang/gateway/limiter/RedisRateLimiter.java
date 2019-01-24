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
        redisTemplate.opsForValue().set("RateLimiter:" + sKey, permits.toByteArray().toString());
    }

    private void setPermits(String sKey, Permits permits) {
        // long ttl = (permits.getMaxPermits() / permits.getRate()) * 2;
        redisTemplate.opsForValue().set("RateLimiter:" + sKey, JSONObject.toJSONString(permits));
    }

    private PermitsEntity.Permits getPermits(String sKey) {
        try {
            String data = redisTemplate.opsForValue().get("RateLimiter:" + sKey);
            if (data != null) {
                return PermitsEntity.Permits.parseFrom(data.getBytes());
            }
            return null;
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Permits getPermit(String sKey) {
        return JSON.parseObject(redisTemplate.opsForValue().get("RateLimiter:" + sKey), Permits.class);
    }

    private PermitsEntity.Permits getPermit() {
        this.redisLocker.lock(this.key, 500, TimeUnit.MILLISECONDS);
        try {
            PermitsEntity.Permits permits = this.getPermits(this.key);
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
            this.redisLocker.unLock(this.key);
        }
    }

    private Permits getDefaultPermits() {
        this.redisLocker.lock(this.key, 500, TimeUnit.MILLISECONDS);
        Permits permits = this.getPermit(this.key);
        try {
            if (permits == null) {
                return new Permits(this.maxPermits, this.maxPermits, this.rate, System.currentTimeMillis());
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            this.redisLocker.unLock(this.key);
        }
        return permits;
    }


    /**
     * 用json序列化的逻辑
     * @param queryPermits
     * @return
     */
    private boolean tryAcquire(long queryPermits) {
        try {
            this.redisLocker.lock( key, 500L, TimeUnit.MILLISECONDS);
            Permits permits = this.getDefaultPermits();
            long fillTokens = (System.currentTimeMillis() - permits.getLastMilliSecond()) / (permits.getRate() * 1000);
            long currentPermits = permits.getCurrentPermits();
            if (fillTokens >= 1) {
                currentPermits = Math.min(permits.getMaxPermits(), currentPermits + fillTokens);
            }
            if (currentPermits - queryPermits >= 0) {
                permits.setCurrentPermits(currentPermits - queryPermits);
                permits.setLastMilliSecond(System.currentTimeMillis());
                this.setPermits(key, permits);
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.redisLocker.unLock(key);
        }
        return false;
    }

    /**
     * 用protobuf序列化的逻辑
     * @param queryPermits
     * @return
     */
    private boolean tryOneAcquire(long queryPermits) {
        try {
            this.redisLocker.lock(key, 500L, TimeUnit.MILLISECONDS);
            PermitsEntity.Permits permits = this.getPermit();
            long fillTokens = (System.currentTimeMillis() - permits.getLastMilliSecond()) / (permits.getRate() * 1000);
            long currentPermits = permits.getCurrentPermits();
            if (fillTokens >= 1) {
                currentPermits = Math.min(permits.getMaxPermits(), currentPermits + fillTokens);
            }
            if (currentPermits - queryPermits >= 0) {
                PermitsEntity.Permits.Builder builder = permits.toBuilder();
                builder.setCurrentPermits(currentPermits - queryPermits);
                builder.setLastMilliSecond(System.currentTimeMillis());
                this.setPermits(key, builder.build());
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.redisLocker.unLock(key);
        }
        return false;
    }

    public boolean tryAcquire() {
        return this.tryOneAcquire(1L);
    }

}
