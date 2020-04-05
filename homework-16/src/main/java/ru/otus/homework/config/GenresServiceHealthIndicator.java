package ru.otus.homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.homework.service.GenreService;


/*
 * Проверяется, если по какой-то причине не заполнена таблица
 * авторов, то сервис Out Of Service
 * В противном случае, сервис Up
 * */

@Component
@RequiredArgsConstructor
public class GenresServiceHealthIndicator implements HealthIndicator {

    private final GenreService genreService;
    private static final String MESSAGE_KEY = "Count Of Authors";

    @Override
    public Health health() {
        long count = genreService.count();
        if (count == 0) {
            return Health.outOfService().withDetail(MESSAGE_KEY, 0).build();
        } else {
            return Health.up().withDetail(MESSAGE_KEY, count).build();
        }
    }
}
