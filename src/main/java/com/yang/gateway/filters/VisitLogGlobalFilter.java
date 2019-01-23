package com.yang.gateway.filters;

import com.yang.gateway.model.VisitLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author jevon
 */
public class VisitLogGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(VisitLogGlobalFilter.class);

    private static final int VISIT_LOG_FILTER_ORDER = 10000;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public int getOrder() {
        return VISIT_LOG_FILTER_ORDER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        try {
            ServerHttpRequest request = exchange.getRequest();
            String remoteAddress = request.getRemoteAddress().toString().substring(1).split(":")[0];
            VisitLogEntity.VisitLog.Builder builder = VisitLogEntity.VisitLog.newBuilder();
            builder.setMethod(request.getMethod().toString());
            builder.setRemoteAddress(remoteAddress);
            log.debug("request path: " + request.getPath());
            log.debug("request cookies: " + request.getCookies().toString());
            log.debug("request param: " + request.getQueryParams().toString());
            kafkaTemplate.send("gateway-log",builder.build().toString());
        } catch (NullPointerException e) {
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }
        }
        return chain.filter(exchange);
    }
}
