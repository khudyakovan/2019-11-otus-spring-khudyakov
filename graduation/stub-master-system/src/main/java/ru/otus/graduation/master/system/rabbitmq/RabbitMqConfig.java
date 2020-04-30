package ru.otus.graduation.master.system.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.graduation.master.system.config.ApplicationConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {

    private final ApplicationConfig config;

    @Bean
    public Declarables createQueues() {

        List<Declarable> d = new ArrayList<>();
        TopicExchange exchange = new TopicExchange(config.getExchange());
        d.add(exchange);

        Map<String, Map<String, String>> services = config.getQueues();
        for (Map.Entry<String, Map<String, String>> service : services.entrySet()) {
            for (Map.Entry<String, String> entry : service.getValue().entrySet()) {
                Queue queue = new Queue(entry.getValue());
                d.add(queue);
                d.add(BindingBuilder.bind(queue).to(exchange).with(entry.getValue()));
            }
        }
        return new Declarables(d);
    }

}