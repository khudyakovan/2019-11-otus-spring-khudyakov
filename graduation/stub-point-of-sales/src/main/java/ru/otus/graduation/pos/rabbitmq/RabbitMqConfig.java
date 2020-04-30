package ru.otus.graduation.pos.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import ru.otus.graduation.pos.config.ApplicationConfig;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {

    private final ApplicationConfig config;

//    @Bean
//    public Declarables pricesDataBindings() {
//        Map<String, String> queueNames = config.getQueues().get("prices");
//        TopicExchange exchange = new TopicExchange(config.getExchange());
//        Queue catalog = new Queue(queueNames.get("pos"));
//        return new Declarables(
//                catalog,
//                exchange,
//                BindingBuilder.bind(catalog).to(exchange).with(queueNames.get("pos"))
//        );
//    }
}
