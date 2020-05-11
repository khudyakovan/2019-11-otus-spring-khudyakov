package ru.otus.graduation.pos.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.graduation.dto.SaleDto;
import ru.otus.graduation.model.Order;
import ru.otus.graduation.model.Sender;
import ru.otus.graduation.model.Status;
import ru.otus.graduation.model.StatusMessage;
import ru.otus.graduation.pos.service.OrderService;
import ru.otus.graduation.pos.service.SaleService;
import ru.otus.graduation.service.StatusEmitterService;

import java.util.Date;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellMockPointOfSale {

    private final StatusEmitterService statusEmitterService;
    private final OrderService orderService;
    private final SaleService saleService;
    private final InputReader inputReader;
    private final ShellHelper shellHelper;
    private static final String SUMMARY_MESSAGE = "Сумма к оплате: %s рублей. Пакет надо? ";

    @ShellMethod("Загрузка данных")
    public void init() {
        statusEmitterService.emitStatusBroadcast(
                new StatusMessage(Sender.POS,
                        -1,
                        -1,
                        "",
                        Status.INIT,
                        new Date()));
    }

    @ShellMethod("Имитация продажи заказа")
    public void getOrder() {
        String orderNumber = inputReader.prompt("Введите номер заказа", "0", true);
        Order order = orderService.findByOrderNumber(Long.parseLong(orderNumber));
        List<SaleDto> details = shellHelper.getSaleDtoList(order);
        shellHelper.render(details, shellHelper.getTableHeader());
        String userInput = inputReader.prompt(String.format(SUMMARY_MESSAGE, shellHelper.getTotal(details)), "0", true);
        if (userInput.equals("Y")) {
            saleService.sale(order.getOrderNumber(), details);
            order.setStatus(Status.COMPLETED);
            saleService.emitOrderStatus(order);
        }
    }


}
