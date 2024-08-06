package com.chinasoft.common.mq;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
//队列和交换机命名
    public static final String REJECT_APPLY_NOTIFY_QUEUE_NAME = "reject.apply.notify.queue";
    public static final String REJECT_APPLY_NOTIFY_EXCHANGE_NAME = "reject.apply.notify.exchange";

    public static class impl{
        public static final String RABBIT_MQ = "rabbitmq";

    }

}
