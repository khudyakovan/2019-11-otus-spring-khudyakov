package ru.otus.graduation.pos.service;

import ru.otus.graduation.dto.SaleDto;
import ru.otus.graduation.model.Order;
import ru.otus.graduation.model.Sale;

import java.util.List;

public interface SaleService {
    Sale sale(long orderNumber, List<SaleDto> details);
    public void emitOrderStatus(Order order);
}
