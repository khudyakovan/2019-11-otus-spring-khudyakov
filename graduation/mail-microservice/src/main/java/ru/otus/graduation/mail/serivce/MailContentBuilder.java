package ru.otus.graduation.mail.serivce;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.otus.graduation.dto.MailMessageDto;
import ru.otus.graduation.dto.OrderItemDto;

import java.text.DecimalFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public String buildCancelled(@NotNull MailMessageDto dto) {
        Context context = this.sharedContent(dto);
        return templateEngine.process("cancelled", context);
    }

    public String buildReady(@NotNull MailMessageDto dto) {
        Context context = this.sharedContent(dto);
        context.setVariable("pickupTime", dto.getPickupTime());
        return templateEngine.process("ready", context);
    }

    public String buildQueued(@NotNull MailMessageDto dto) {
        Context context = this.sharedContent(dto);
        context.setVariable("password", this.oneTimePassword(dto));
        return templateEngine.process("queued", context);
    }

    private Context sharedContent(@NotNull MailMessageDto dto){
        Context context = new Context();
        context.setVariable("orderNumber", dto.getOrderNumber());
        context.setVariable("details", dto.getDetails());
        context.setVariable("total", df.format(this.getTotal(dto.getDetails())));
        return context;
    }

    private String oneTimePassword(MailMessageDto dto) {
        return String.format("%s%s",
                dto.getMobileNumber().substring(dto.getMobileNumber().length() - 4),
                dto.getProposalNumber());
    }

    public float getTotal(List<OrderItemDto> list){
        return list.stream().reduce(0f, (acc, item) -> acc + item.getSummary(), Float::sum);
    }
}
