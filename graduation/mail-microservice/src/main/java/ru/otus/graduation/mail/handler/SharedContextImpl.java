package ru.otus.graduation.mail.handler;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import ru.otus.graduation.dto.MailMessageDto;
import ru.otus.graduation.dto.OrderItemDto;

import java.text.DecimalFormat;
import java.util.List;

@Component
public class SharedContextImpl implements SharedContext {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public Context sharedContext(@NotNull MailMessageDto dto){
        Context context = new Context();
        context.setVariable("orderNumber", dto.getOrderNumber());
        context.setVariable("details", dto.getDetails());
        context.setVariable("total", df.format(this.getTotal(dto.getDetails())));
        return context;
    }

    private float getTotal(List<OrderItemDto> list){
        return list.stream().reduce(0f, (acc, item) -> acc + item.getSummary(), Float::sum);
    }

    public String oneTimePassword(MailMessageDto dto) {
        return String.format("%s%s",
                dto.getMobileNumber().substring(dto.getMobileNumber().length() - 4),
                dto.getProposalNumber());
    }
}
