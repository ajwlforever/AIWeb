package com.yqx.ai.manager;


import com.yqx.ai.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class ServiceMessageSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message){
        rabbitTemplate.convertAndSend(RabbitMqConfig.MAIN_DIRECT_EXCHANGE, RabbitMqConfig.DATA_ANALYSIS_ROUTE_KEY, "lalal" );
    }
}
