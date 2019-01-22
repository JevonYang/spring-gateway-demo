package com.yang.gateway.filters;

import com.yang.gateway.service.LuaRateLimiterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author jevon
 */
public class GlobalIpAccessFrequencyFilter implements GlobalFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(GlobalIpAccessFrequencyFilter.class);

    @Autowired
    private LuaRateLimiterService rateLimiterService;

    @Value("${spring.cloud.gateway.ip-access-frequency.max-permits}")
    private String maxPermits;

    @Value("${spring.cloud.gateway.ip-access-frequency.rate}")
    private String rate;

    private static String REMOTE_ADDRESS_RATE_LIMITER_PREFIX = "RateLimiter:Ip:";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String ip = exchange.getRequest().getRemoteAddress().toString();

        Long secondMills = System.currentTimeMillis();
        if (rateLimiterService.acquire(REMOTE_ADDRESS_RATE_LIMITER_PREFIX+ip, maxPermits, rate, "1", secondMills.toString()) == 1) {
            logger.info("================"+ip+"================");
            return chain.filter(exchange);
        }
        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
