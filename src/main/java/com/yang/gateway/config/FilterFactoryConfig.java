package com.yang.gateway.config;

import com.yang.gateway.filters.GlobalIpAccessFrequencyGlobalFilter;
import com.yang.gateway.filters.MipRateLimiterGatewayFilterFactory;
import com.yang.gateway.filters.VisitLogGlobalFilter;
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
    public VisitLogGlobalFilter getVisitLogFilter() {
        return new VisitLogGlobalFilter();
    }

    @Bean
    public GlobalIpAccessFrequencyGlobalFilter globalIpAccessFrequencyFilter() {
        return new GlobalIpAccessFrequencyGlobalFilter();
    }
}
