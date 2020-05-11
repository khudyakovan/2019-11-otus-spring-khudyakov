package ru.otus.graduation.mail.serivce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.otus.graduation.dto.MailMessageDto;
import ru.otus.graduation.service.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMqListenerService {

    private final ObjectMapper objectMapper;
    private final MailService mailService;
    private final UserService userService;
    private static final String MAIL_SENT = "STATUS MESSAGE sent to %s. ORDER #%s";

    @RabbitListener(queues = "${application.rabbit.queues.products.orders-to-mail}")
    public void processMailMessage(String message) throws JsonProcessingException {
        MailMessageDto dto = objectMapper.readValue(message, new TypeReference<MailMessageDto>() {
        });
        mailService.sendMailMessage(dto);
        log.info(String.format(MAIL_SENT, dto.getEmail(), dto.getOrderNumber()));
    }
}
