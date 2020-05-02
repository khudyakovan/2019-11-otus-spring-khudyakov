package ru.otus.graduation.pos.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.graduation.model.Sender;
import ru.otus.graduation.model.Status;
import ru.otus.graduation.model.StatusMessage;
import ru.otus.graduation.service.StatusEmitterService;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class StartUpInit {

    private final StatusEmitterService service;

    @PostConstruct
    public void init() {
        service.emitStatus(new StatusMessage(Sender.POS,-1,-1,"", Status.INIT, new Date()));
    }
}
