package ru.otus.graduation.mail.serivce;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import ru.otus.graduation.dto.MailMessageDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;
    private static final String MAIL_SUBJECT = "Информация по заказу %s";

    public void sendMailMessage(MailMessageDto dto){
        MimeMessagePreparator mail = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(dto.getEmail());
            messageHelper.setSubject(String.format(MAIL_SUBJECT, dto.getOrderNumber()));
            String content = "";
            switch (dto.getStatus()){
                case CANCELLED:
                    content = mailContentBuilder.buildCancelled(dto);
                    break;
                case READY:
                    content = mailContentBuilder.buildReady(dto);
                    break;
                case QUEUED:
                    content = mailContentBuilder.buildQueued(dto);
                    break;
                default:
                    return;
            }
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(mail);
        } catch (MailException e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
