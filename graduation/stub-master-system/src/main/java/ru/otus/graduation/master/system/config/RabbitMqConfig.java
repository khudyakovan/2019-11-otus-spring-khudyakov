package ru.otus.graduation.master.system.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.graduation.config.ApplicationConfig;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {

    private final ApplicationConfig config;
    private static final String MAIN_EXCHANGE= "main";
    private static final String STATUS_EXCHANGE= "status";

    @Bean
    public Declarables createQueues() {

        List<Declarable> d = new ArrayList<>();

        //Main Exchange and Queues initialization

        TopicExchange topicExchange = new TopicExchange(config.getExchangeByPropertyName(MAIN_EXCHANGE));
        d.add(topicExchange);

        config.getAllQueueGroups().forEach(groupName ->{
            config.getAllQueueNamesByGroupName(groupName).forEach(queueName ->{
                Queue queue = new Queue(queueName);
                d.add(queue);
                d.add(BindingBuilder.bind(queue).to(topicExchange).with(queueName));
                d.add(BindingBuilder.bind(queue).to(topicExchange).with(queueName+".*"));
            });
        } );

        //Broadcast Status Exchange and Queues initialization
        String name = config.getExchangeByPropertyName(STATUS_EXCHANGE);
        FanoutExchange fanoutExchange = new FanoutExchange(name);
        d.add(fanoutExchange);
        Queue broadcastQueue = new Queue(name);
        d.add(broadcastQueue);
        d.add(BindingBuilder.bind(broadcastQueue).to(fanoutExchange));

        return new Declarables(d);
    }

}
