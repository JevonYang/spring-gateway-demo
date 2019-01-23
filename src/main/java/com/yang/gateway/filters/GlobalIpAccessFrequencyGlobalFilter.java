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
public class GlobalIpAccessFrequencyGlobalFilter implements GlobalFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(GlobalIpAccessFrequencyGlobalFilter.class);

    @Autowired
    private LuaRateLimiterService rateLimiterService;

    @Value("${spring.cloud.gateway.ip-access-frequency.max-permits}")
    private String maxPermits;

    @Value("${spring.cloud.gateway.ip-access-frequency.rate}")
    private String rate;

    private static String REMOTE_ADDRESS_RATE_LIMITER_PREFIX = "RateLimiter:IP:";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            String ip = exchange.getRequest().getRemoteAddress().toString().substring(1).split(":")[0];
            if (rateLimiterService.acquire(REMOTE_ADDRESS_RATE_LIMITER_PREFIX+ip, maxPermits, rate, "1", System.currentTimeMillis()+"") == 1) {
                logger.debug("================"+ip+"================");
                return chain.filter(exchange);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
