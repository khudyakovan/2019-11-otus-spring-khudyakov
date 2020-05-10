package ru.otus.graduation.orders.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private String id;
    private String name;
    private int quantity;
}
