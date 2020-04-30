package ru.otus.graduation.catalogue.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {

//    private final ApplicationConfig config;
//
//    @Bean
//    public Declarables levelsDataBindings() {
//        Map<String, String> queueNames = config.getQueues().get("levels");
//        TopicExchange topicExchange = new TopicExchange(config.getExchange());
//        Queue catalog = new Queue(queueNames.get("catalog"));
//        return new Declarables(
//                catalog,
//                topicExchange,
//                BindingBuilder.bind(catalog).to(topicExchange).with(queueNames.get("catalog"))
//        );
//    }
//
//    @Bean
//    public Declarables pricesDataBindings() {
//        Map<String, String> queueNames = config.getQueues().get("prices");
//        TopicExchange topicExchange = new TopicExchange(config.getExchange());
//        Queue catalog = new Queue(queueNames.get("catalog"));
//        return new Declarables(
//                catalog,
//                topicExchange,
//                BindingBuilder.bind(catalog).to(topicExchange).with(queueNames.get("catalog"))
//        );
//    }
}
