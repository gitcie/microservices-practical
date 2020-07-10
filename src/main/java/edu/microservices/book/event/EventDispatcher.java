/******************************************************************************
 * Copyright (C) 2017 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package edu.microservices.book.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * class summary
 * <p>
 * class detail description
 *
 * @author Siyi Lu
 * @since 2020/7/6
 */
@Component
public class EventDispatcher {

    private RabbitTemplate rabbitTemplate;

    private String multiplicationExchange;

    private String multiplicationSolvedRoutingKey;

    @Autowired
    EventDispatcher(
            final RabbitTemplate rabbitTemplate,
            @Value("${multiplication.exchange}") final String multiplicationExchange,
            @Value("${multiplication.solved.key}") final String multiplicationSolvedRoutingKey
    ){
        this.rabbitTemplate = rabbitTemplate;
        this.multiplicationExchange = multiplicationExchange;
        this.multiplicationSolvedRoutingKey = multiplicationSolvedRoutingKey;
    }

    public void sendMultiplicationSolvedEvent(final MultiplicationSolvedEvent event) {
        rabbitTemplate.convertAndSend(
                multiplicationExchange,
                multiplicationSolvedRoutingKey,
                event
        );
    }


}
