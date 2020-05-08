package ru.otus.graduation.orders.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.otus.graduation.model.User;

public interface UserService {
    User findByUsername(String username) throws JsonProcessingException;
}
