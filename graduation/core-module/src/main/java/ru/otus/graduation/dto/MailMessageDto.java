package ru.otus.graduation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.graduation.model.Status;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailMessageDto implements Serializable {
    private long orderNumber;
    private long proposalNumber;
    private String mobileNumber;
    private String email;
    private String pickupTime;
    private Date currentDate;
    private List<OrderItemDto> details;
    private Status status;
}
