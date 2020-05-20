package ru.otus.graduation.mail.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import ru.otus.graduation.dto.MailMessageDto;
import ru.otus.graduation.mail.handler.MessageHandler;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final List<MessageHandler> list;
    private static final String MAIL_SUBJECT = "Информация по заказу %s";

    public void sendMailMessage(MailMessageDto dto){
        MimeMessagePreparator mail = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(dto.getEmail());
            messageHelper.setSubject(String.format(MAIL_SUBJECT, dto.getOrderNumber()));
            String content = "";
            for(MessageHandler handler : list){
                if(handler.getHandlerType() == dto.getStatus()) {
                    content = handler.handleMessage(dto);
                    break;
                }
            };
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(mail);
        } catch (MailException e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
