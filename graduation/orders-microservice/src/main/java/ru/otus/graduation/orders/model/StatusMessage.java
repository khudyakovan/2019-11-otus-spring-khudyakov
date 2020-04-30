package ru.otus.graduation.orders.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusMessage {
    private int basketNumber;
    private int orderNumber;
    private String status;
    private Date currentDate;
}
