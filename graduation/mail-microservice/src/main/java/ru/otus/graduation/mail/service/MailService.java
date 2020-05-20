package ru.otus.graduation.mail.service;

import ru.otus.graduation.dto.MailMessageDto;

public interface MailService {

    void sendMailMessage(MailMessageDto dto);
}
