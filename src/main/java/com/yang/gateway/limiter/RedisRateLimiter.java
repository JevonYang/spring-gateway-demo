package com.yang.gateway.limiter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.math.LongMath;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import java.util.concurrent.TimeUnit;


/**
 * @author jevon
 */
@Data
public class RedisRateLimiter {

    private Logger log = LoggerFactory.getLogger(RedisRateLimiter.class);

    private JedisCluster jedisCluster;

    /**
     * requestId
     */
    private String requestId;

    /**
     * redis key
     */
    private String key;
    /**
     * redis分布式锁的key
     * @return
     */
    private String lockKey;
    /**
     * 每秒存入的令牌数
     */
    private Double permitsPerSecond;
    /**
     * 最大存储maxBurstSeconds秒生成的令牌
     */
    private Integer maxBurstSeconds;
    /**
     * 分布式同步锁
     */
    private RedisLocker syncLock;

    public Long getNowTime() {
        String script = "local a=redis.call('TIME'); return a[1]*1000+a[2]";
        return (Long) jedisCluster.eval(script,"time");
    }

    public boolean putObject(String sKey, PermitsEntity.Permits permits, int expiredInSeconds) {
        boolean result = false;

        if (StringUtils.isEmpty(sKey)) {
            return result;
        }

        if (this.jedisCluster != null) {
            byte[] arrKey = ("Permits:"+ sKey).getBytes();

            this.jedisCluster.set(arrKey, permits.toByteArray());
            if (expiredInSeconds > 0) {
                this.jedisCluster.expire("Permits:"+sKey, expiredInSeconds+1000);
            }
            result = true;
        }

        return result;
    }

    public PermitsEntity.Permits getObject(String sKey) {
        PermitsEntity.Permits result = null;
        if (StringUtils.isEmpty(sKey)) {
            return result;
        };
        try {
            if (this.jedisCluster != null) {
                byte[] arrKey =("Permits:" + sKey).getBytes();
                byte[] data = this.jedisCluster.get(arrKey);
                if (data != null && data.length >0) {
                    result = PermitsEntity.Permits.parseFrom(data);
                }
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return result;
    };

    public RedisRateLimiter(String key, Double permitsPerSecond, Integer maxBurstSeconds, RedisLocker syncLock, String requestId, JedisCluster jedisCluster){
        this.key = key;
        this.lockKey = "DISTRIBUTED_LOCK:" + key;
        this.permitsPerSecond = permitsPerSecond;
        this.maxBurstSeconds = maxBurstSeconds;
        this.syncLock = syncLock;
        this.requestId=requestId;
        this.jedisCluster=jedisCluster;
    }

    /**
     * 生成并存储默认令牌桶
     * @return
     */
    private PermitsEntity.Permits putDefaultPermits() {
        this.lock();
        try{
            PermitsEntity.Permits permits = getObject(key);

            if( null == permits ){
                permits = RedisPermits.getPermits(this.permitsPerSecond, this.maxBurstSeconds);
                putObject(key, permits, (int)RedisPermits.expires(permits));
                return permits;
            }else{
                return permits;
            }
        }finally {
            this.unlock();
        }

    }

    /**
     * 加锁
     */
    private void lock(){
        syncLock.tryGetDistributedLock(lockKey, this.requestId, 500000);
    }

    /**
     * 解锁
     */
    private void unlock(){
        syncLock.releaseDistributedLock(lockKey, this.requestId);
    }

    /**
     * 获取令牌桶
     * @return
     */
    public PermitsEntity.Permits getPermits() {
        PermitsEntity.Permits permits = getObject(key);
        if( null == permits ){
            return putDefaultPermits();
        }
        return permits;
    }

    /**
     * 更新令牌桶
     * @param permits
     */
    public void setPermits(PermitsEntity.Permits permits) {
        putObject(key, permits, (int) RedisPermits.expires(permits));
    }

    /**
     * 等待直到获取指定数量的令牌
     * @param tokens
     * @return
     * @throws InterruptedException
     */
    public Long acquire(Long tokens) throws InterruptedException {
        long milliToWait = this.reserve(tokens);
        log.info("acquire for {}ms {}", milliToWait, Thread.currentThread().getName());
        Thread.sleep(milliToWait);
        return milliToWait;
    }

    /**
     * 获取1一个令牌
     * @return
     * @throws InterruptedException
     */
    private long acquire() throws InterruptedException{
        return acquire(1L);
    }

    /**
     *
     * @param tokens 要获取的令牌数
     * @param timeout 获取令牌等待的时间，负数被视为0
     * @param unit
     * @return
     * @throws InterruptedException
     */
    private Boolean tryAcquire(Long tokens, Long timeout, TimeUnit unit) throws InterruptedException{
        long timeoutMicros = Math.max(unit.toMillis(timeout), 0);
        checkTokens(tokens);
        Long milliToWait;
        try {
            this.lock();
            if (!this.canAcquire(tokens, timeoutMicros)) {
                return false;
            } else {
                milliToWait = Math.max(this.reserveAndGetWaitLength(tokens), 0);
            }
        } finally {
            this.unlock();
        }
        Thread.sleep(milliToWait);
        return true;
    }

    /**
     * 获取一个令牌
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    public Boolean tryAcquire(Long timeout , TimeUnit unit) throws InterruptedException{
        return tryAcquire(1L,timeout, unit);
    }

    private long redisNow(){
        Long time = getNowTime();
        return null == time ? System.currentTimeMillis() : time;
    }

    /**
     * 获取令牌n个需要等待的时间
     * @param tokens
     * @return
     */
    private long reserve(Long tokens) {
        this.checkTokens(tokens);
        try {
            this.lock();
            return this.reserveAndGetWaitLength(tokens);
        } finally {
            this.unlock();
        }
    }

    /**
     * 校验token值
     * @param tokens
     */
    private void checkTokens(Long tokens) {
        if( tokens < 0 ){
            throw new IllegalArgumentException("Requested tokens " + tokens + " must be positive");
        }
    }

    /**
     * 在等待的时间内是否可以获取到令牌
     * @param tokens
     * @param timeoutMillis
     * @return
     */
    private Boolean canAcquire(Long tokens, Long timeoutMillis){
        return queryEarliestAvailable(tokens) - timeoutMillis <= 0;
    }

    /**
     * 返回获取{tokens}个令牌最早可用的时间
     * @param tokens
     * @return
     */
    private Long queryEarliestAvailable(Long tokens){
        long n = redisNow();
        PermitsEntity.Permits permit = this.getPermits();
        RedisPermits.resync(permit, n);
        // permit.reSync(n);
        // 可以消耗的令牌数
        long storedPermitsToSpend = Math.min(tokens, permit.getStoredPermits());
        // 需要等待的令牌数
        long freshPermits = tokens - storedPermitsToSpend;
        // 需要等待的时间
        long waitMillis = freshPermits * permit.getIntervalMillis();
        return LongMath.saturatedAdd(permit.getNextFreeTicketMillis() - n, waitMillis);
    }

    /**
     * 预定@{tokens}个令牌并返回所需要等待的时间
     * @param tokens
     * @return
     */
    private Long reserveAndGetWaitLength(Long tokens){
        long n = redisNow();
        PermitsEntity.Permits permit = this.getPermits();
        RedisPermits.resync(permit, n);
        // 可以消耗的令牌数
        long storedPermitsToSpend = Math.min(tokens, permit.getStoredPermits());
        // 需要等待的令牌数
        long freshPermits = tokens - storedPermitsToSpend;
        // 需要等待的时间
        long waitMillis = freshPermits * permit.getIntervalMillis();

        long updatedNextFreeTicketMills = LongMath.saturatedAdd(permit.getNextFreeTicketMillis(), waitMillis);

        PermitsEntity.Permits.Builder builder = permit.toBuilder().setStoredPermits(permit.getStoredPermits() - storedPermitsToSpend);
        builder.setNextFreeTicketMillis(updatedNextFreeTicketMills);
        permit = builder.build();
        this.setPermits(permit);
        return updatedNextFreeTicketMills - n;
    }

}
