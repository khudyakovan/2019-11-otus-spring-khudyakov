package ru.otus.graduation.mail.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.otus.graduation.dto.MailMessageDto;
import ru.otus.graduation.model.Status;

@Component
@RequiredArgsConstructor
public class QueuedHandler implements MessageHandler{

    private final SharedContext sharedContext;
    private final TemplateEngine templateEngine;

    @Override
    public String handleMessage(MailMessageDto dto) {
        Context context = sharedContext.sharedContext(dto);
        context.setVariable("password", sharedContext.oneTimePassword(dto));
        return templateEngine.process("queued", context);
    }

    @Override
    public Status getHandlerType() {
        return Status.QUEUED;
    }
}
