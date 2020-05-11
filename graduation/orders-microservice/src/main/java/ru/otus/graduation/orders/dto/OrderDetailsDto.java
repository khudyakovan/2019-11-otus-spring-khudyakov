package ru.otus.graduation.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.graduation.dto.OrderItemDto;
import ru.otus.graduation.model.Status;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {
    private long orderNumber;
    private Status status;
    private List<OrderItemDto> orderItems;
}
