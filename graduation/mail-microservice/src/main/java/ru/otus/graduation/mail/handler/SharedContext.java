package ru.otus.graduation.mail.handler;

import org.jetbrains.annotations.NotNull;
import org.thymeleaf.context.Context;
import ru.otus.graduation.dto.MailMessageDto;

public interface SharedContext {

    Context sharedContext(@NotNull MailMessageDto dto);
    String oneTimePassword(MailMessageDto dto);
}
