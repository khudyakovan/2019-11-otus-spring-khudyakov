package ru.otus.graduation.mail.serivce;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootTest
@Import(JavaMailSenderImpl.class)
class MailServiceTest {

    @Autowired
    MailService mailService;


    @Test
    void sendMailMessage() {

    }
}
