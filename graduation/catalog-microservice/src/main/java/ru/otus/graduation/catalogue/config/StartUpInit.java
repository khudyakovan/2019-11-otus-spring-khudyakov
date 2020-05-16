package ru.otus.graduation.catalogue.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.otus.graduation.model.Sender;
import ru.otus.graduation.model.Status;
import ru.otus.graduation.model.StatusMessage;
import ru.otus.graduation.service.StatusEmitterService;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class StartUpInit implements ApplicationListener<ApplicationReadyEvent> {

    private final StatusEmitterService service;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        service.emitStatusBroadcast(new StatusMessage(Sender.CATALOG,-1,-1,"", Status.INIT, new Date()));
    }
}
