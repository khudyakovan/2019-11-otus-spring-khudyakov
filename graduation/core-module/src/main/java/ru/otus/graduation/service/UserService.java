package ru.otus.graduation.service;

import ru.otus.graduation.model.User;

public interface UserService {
    User findByUsername(String username);
}
