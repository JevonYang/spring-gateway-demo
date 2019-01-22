package com.yang.gateway.filters;

import com.google.common.util.concurrent.RateLimiter;
//import com.yang.gateway.limiter.RateLimiterFactory;
//import com.yang.gateway.limiter.RedisRateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
//import redis.clients.jedis.JedisCluster;


import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * @author jevon
 */
public class MipRateLimiterGatewayFilterFactory extends AbstractGatewayFilterFactory<MipRateLimiterGatewayFilterFactory.Config> {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

//    @Autowired
//    private JedisCluster jedisCluster;
//
//    @Autowired
//    private RateLimiterFactory rateLimiterFactory;

    public double permitsPerSecond = 1000;

    public static int count=0;

    public static RateLimiter rateLimiter;

    // public static RedisRateLimiter redisRateLimiter;
    // = RateLimiter.create(new Config().getPermitsPerSecond());

    public MipRateLimiterGatewayFilterFactory(Class<Config> configClass) {
        super(configClass);
        // this.permitsPerSecond = permitsPerSecond;
    }

    @Override
    public GatewayFilter apply(Config config) {

        rateLimiter = (rateLimiter == null) ? RateLimiter.create(config.permitsPerSecond): rateLimiter;
        // redisRateLimiter = (redisRateLimiter == null) ? rateLimiterFactory.build(config.limiterName, config.permitsPerSecond, config.maxBurstSeconds, jedisCluster): redisRateLimiter;

        return (exchange, chain) -> {

            try {
                if (rateLimiter.tryAcquire()) {
                    return chain.filter(exchange);
                }

//                if (redisRateLimiter.tryAcquire(5000L, TimeUnit.MILLISECONDS)) {
//                    return chain.filter(exchange);
//                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            };
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            ByteBuffer byteBuffer = ByteBuffer.wrap ("服务访问过于频繁，请稍后再试".getBytes());
            DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap(byteBuffer);
            exchange.getResponse().writeWith(Mono.just(dataBuffer));
            return exchange.getResponse().setComplete();
        };
    }

    public static class Config {
        private double permitsPerSecond;

        private String limiterName;

        private Integer maxBurstSeconds;

        public Integer getMaxBurstSeconds() {
            return maxBurstSeconds;
        }

        public void setMaxBurstSeconds(Integer maxBurstSeconds) {
            this.maxBurstSeconds = maxBurstSeconds;
        }

        public String getLimiterName() {
            return limiterName;
        }

        public void setLimiterName(String limiterName) {
            this.limiterName = limiterName;
        }

        public double getPermitsPerSecond() {
            return permitsPerSecond;
        }

        public void setPermitsPerSecond(double permitsPerSecond) {
            this.permitsPerSecond = permitsPerSecond;
        }
    }

}
