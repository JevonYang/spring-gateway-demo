package com.yang.gateway.filters;

import com.google.common.util.concurrent.RateLimiter;
import com.yang.gateway.limiter.RateLimiterFactory;
import com.yang.gateway.limiter.RedisRateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;

/**
 * @author jevon
 */
public class MipRateLimiterGatewayFilterFactory extends AbstractGatewayFilterFactory<MipRateLimiterGatewayFilterFactory.Config> {

    @Autowired
    private RateLimiterFactory rateLimiterFactory;

    public double permitsPerSecond = 1000;

    public static int count = 0;

    public static RateLimiter rateLimiter;

    public static RedisRateLimiter redisRateLimiter;

    public MipRateLimiterGatewayFilterFactory(Class<Config> configClass) {
        super(configClass);
    }

    @Override
    public GatewayFilter apply(Config config) {

        rateLimiter = (rateLimiter == null) ? RateLimiter.create(config.rate) : rateLimiter;
        redisRateLimiter = (redisRateLimiter == null) ? rateLimiterFactory.build("RateLimiter:" + config.name, config.rate, config.maxPermits) : redisRateLimiter;

        return (exchange, chain) -> {

            try {
                if (rateLimiter.tryAcquire()) {
                    return chain.filter(exchange);
                }
                if (redisRateLimiter.tryAcquire()) {
                    return chain.filter(exchange);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            ByteBuffer byteBuffer = ByteBuffer.wrap("服务访问过于频繁，请稍后再试".getBytes());
            DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap(byteBuffer);
            exchange.getResponse().writeWith(Mono.just(dataBuffer));
            return exchange.getResponse().setComplete();
        };
    }

    public static class Config {

        private String name;

        private long maxPermits;

        private long rate;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getMaxPermits() {
            return maxPermits;
        }

        public void setMaxPermits(long maxPermits) {
            this.maxPermits = maxPermits;
        }

        public long getRate() {
            return rate;
        }

        public void setRate(long rate) {
            this.rate = rate;
        }
    }

}
