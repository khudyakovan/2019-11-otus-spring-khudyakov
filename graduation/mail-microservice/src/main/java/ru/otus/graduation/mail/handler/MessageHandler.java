package ru.otus.graduation.mail.handler;

import ru.otus.graduation.dto.MailMessageDto;
import ru.otus.graduation.model.Status;

public interface MessageHandler {
    String handleMessage(MailMessageDto dto);
    Status getHandlerType();
}
