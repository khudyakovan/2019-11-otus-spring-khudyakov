package ru.otus.graduation.dto;

import lombok.Data;

@Data
public class SaleDto {
    private String id;
    private String name;
    private int quantity;
    private float price;
    private float summary;
}
