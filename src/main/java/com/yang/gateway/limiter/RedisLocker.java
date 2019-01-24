package com.yang.gateway.limiter;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author jevon
 */
public class RedisLocker {

    private static Logger logger = LoggerFactory.getLogger(RedisLocker.class);

    private RedissonClient redissonClient;

    public RedisLocker(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public RLock lock(String lockKey) {
        RLock rLock = this.getRLock("Lock:" +lockKey);
        rLock.lock();
        return rLock;
    }

    public RLock lock(String lockKey, long leaseTime) {
        return this.lock("Lock:" +lockKey, leaseTime, TimeUnit.SECONDS);
    }

    public RLock lock(String lockKey, long leaseTime, TimeUnit timeUnit) {
        RLock rLock = this.getRLock("Lock:" +lockKey);
        rLock.lock(leaseTime, timeUnit);
        return rLock;
    }

    public boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit timeUnit) {
        RLock rLock = this.getRLock("Lock:" +lockKey);
        try {
            return rLock.tryLock(waitTime, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        return false;
    }

    public void unLock(String lockKey) {
        RLock rLock = this.getRLock("Lock:" +lockKey);
        rLock.unlock();
    }


    public void unLock(RLock rLock) {
        if( null == rLock ){
            throw new NullPointerException("rLock cannot be null.");
        }
        rLock.unlock();
    }

    private RLock getRLock(String lockKey) {
        if( null == this.redissonClient ){
            throw new NullPointerException("redisson client is null.");
        }
        return this.redissonClient.getLock("Lock:" +lockKey);
    }

}
