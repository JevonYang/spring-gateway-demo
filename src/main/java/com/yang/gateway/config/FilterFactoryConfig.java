package com.yang.gateway.config;

import com.yang.gateway.filters.MipRateLimiterGatewayFilterFactory;
import com.yang.gateway.filters.VisitLogFilter;
import com.yang.gateway.utils.RedisUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jevon
 */
@Configuration
public class FilterFactoryConfig {

    @Bean
    public MipRateLimiterGatewayFilterFactory mipRateLimiterGatewayFilterFactory() {
        return new MipRateLimiterGatewayFilterFactory(MipRateLimiterGatewayFilterFactory.Config.class);
    }

    @Bean
    public VisitLogFilter getVisitLogFilter() {
        return new VisitLogFilter();
    }

    @Bean
    public RedisUtils getRedisUtils() {
        return new RedisUtils();
    }

}
