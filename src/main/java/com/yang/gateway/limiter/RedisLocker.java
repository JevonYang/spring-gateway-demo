package com.yang.gateway.limiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author jevon
 */

//@Component("redisLocker")
public class RedisLocker {

//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//
//    private static final String LOCK_SUCCESS = "OK";
//    private static final String SET_IF_NOT_EXIST = "NX";
//    private static final String SET_WITH_EXPIRE_TIME = "PX";
//    private static final Long RELEASE_SUCCESS = 1L;
//
//    /**
//     * 尝试获取分布式锁
//     * @param jedis Redis客户端
//     * @param lockKey 锁
//     * @param requestId 请求标识
//     * @param expireTime 超期时间
//     * @return 是否获取成功
//     */
//    public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
//
//        redisTemplate. setnx
//
////        String result = this.jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
////
////        if (LOCK_SUCCESS.equals(result)) {
////            return true;
////        }
//        return false;
//
//    }
//
//
//
//    /**
//     * 释放分布式锁
//     * @param jedis Redis客户端
//     * @param lockKey 锁
//     * @param requestId 请求标识
//     * @return 是否释放成功
//     */
//    public boolean releaseDistributedLock(String lockKey, String requestId) {
//
//        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
//        Object result = this.jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
//
//        if (RELEASE_SUCCESS.equals(result)) {
//            return true;
//        }
//        return false;
//
//    }

}
