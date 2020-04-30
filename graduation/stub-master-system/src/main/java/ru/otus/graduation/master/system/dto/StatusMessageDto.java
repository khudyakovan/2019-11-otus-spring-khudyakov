package ru.otus.graduation.master.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusMessageDto {
    private int basketNumber;
    private int orderNumber;
    private String status;
    private Date currentDate;
}
