package ru.otus.graduation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusMessage {
    private Sender sender;
    private int basketNumber;
    private int orderNumber;
    private String phoneNumber;
    private Status status;
    private Date currentDate;
}
