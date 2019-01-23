package com.yang.gateway.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @author yangzijing
 * @date 2018/09/30
 */
//@Configuration
//@ConditionalOnProperty(value = "spring.kafka.bootstrap-servers")
public class RoutesRefreshListener implements ApplicationEventPublisherAware {

    private static final Logger logger = LoggerFactory.getLogger(RoutesRefreshListener.class);

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

//    @KafkaListener(topics = { "gateway" })
//    public void listen(ConsumerRecord<?, ?> record) {
//        logger.info("---------->kafka的key: {} ------>kafka的value: {}", record.key(), record.value().toString());
//        this.publisher.publishEvent(new RefreshRoutesEvent(this));
//    }

//    @KafkaListener(topics = { "gateway-log" })
//    public void gatewayLogger(ConsumerRecord<?, ?> record) {
//        logger.info("gateway-log key: {}; gateway-log value: {}", record.key(), record.value().toString());
//    }
}
