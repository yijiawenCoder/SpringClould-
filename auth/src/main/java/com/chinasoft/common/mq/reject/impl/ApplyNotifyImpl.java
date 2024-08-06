package com.chinasoft.common.mq.reject.impl;

import com.chinasoft.common.mq.MqConfig;
import com.chinasoft.common.mq.reject.ApplyNotify;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@Profile(MqConfig.impl.RABBIT_MQ)
public class ApplyNotifyImpl extends ApplyNotify {

    @Resource
    private AmqpAdmin amqpAdmin;
    @Resource

    private AmqpTemplate amqpTemplate;
    //mq初始化
    @PostConstruct
    public void init(){
        //使用频道捆绑交换机和队列
        DirectExchange exchange= new DirectExchange(MqConfig.REJECT_APPLY_NOTIFY_EXCHANGE_NAME);
        //开启延时
        exchange.setDelayed(true);
        Queue queue = new Queue(MqConfig.REJECT_APPLY_NOTIFY_QUEUE_NAME);
        Binding binding = BindingBuilder.bind(queue).to(exchange).withQueueName();
        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(binding);
    }
    @Override
    public void send(String msg) {

        _log.info("发送mq消息： msg={}",msg);
        amqpTemplate.convertAndSend(MqConfig.REJECT_APPLY_NOTIFY_QUEUE_NAME,msg);

    }

    @Override
    public void send(String msg, long delay) {
        _log.info("发送mq延时消息： msg={}，delay={}",msg,delay);
        amqpTemplate.convertAndSend(MqConfig.REJECT_APPLY_NOTIFY_EXCHANGE_NAME, MqConfig.REJECT_APPLY_NOTIFY_QUEUE_NAME, msg,
                new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        message.getMessageProperties().setDelay((int)delay);
                        return message;
                    }
                });

    }


    @RabbitListener(queues = MqConfig.REJECT_APPLY_NOTIFY_QUEUE_NAME)
    public void onMessage(String msg){
        receive(msg);
    }
}
