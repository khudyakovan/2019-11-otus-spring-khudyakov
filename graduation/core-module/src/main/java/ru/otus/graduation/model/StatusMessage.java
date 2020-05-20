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
    private long proposalNumber;
    private long orderNumber;
    private String mobileNumber;
    private Status status;
    private Date currentDate;
}
