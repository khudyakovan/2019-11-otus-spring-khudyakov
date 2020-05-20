package ru.otus.graduation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "application.rabbit")
public class ApplicationConfig {
    @Value("${application.shop-id}")
    private String shopId;

    private Map<String, String> exchanges;
    private Map<String, Map<String, String>> queues;

    public String getExchangeByPropertyName(String propertyName){
        return this.exchanges.get(propertyName);
    }

    public List<String> getAllExchanges(){
        return new ArrayList<>(exchanges.values());
    }

    public List<String> getAllQueueGroups(){
        return new ArrayList<>(queues.keySet());
    }

    public List<String> getAllQueueNamesByGroupName(String queueGroupName){
        return new ArrayList<>(queues.get(queueGroupName).values());
    }

    public List<String> getAllQueuePropertyNamesByGroupName(String queueGroupName){
        return new ArrayList<>(queues.get(queueGroupName).keySet());
    }

    public String getQueueNameByGroupNameAndPropertyName(String groupName, String propertyName){
        return queues.get(groupName).get(propertyName);
    };

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setExchanges(Map<String, String> exchanges) {
        this.exchanges = exchanges;
    }

    public void setQueues(Map<String, Map<String, String>> queues) {
        this.queues = queues;
    }

    public String getShopId() {
        return this.shopId;
    }
}
