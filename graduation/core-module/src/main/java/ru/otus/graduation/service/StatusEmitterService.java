package ru.otus.graduation.service;

import ru.otus.graduation.model.StatusMessage;

public interface StatusEmitterService {
    void emitStatusBroadcast(StatusMessage message);
    void emitStatusToSpecificQueue(String exchangeName, String queueGroup, String queue, Object message);
}
