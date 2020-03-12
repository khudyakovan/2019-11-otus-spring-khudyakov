package ru.otus.homework.service;

import ru.otus.homework.entity.User;

import java.util.List;

public interface UserService {
    User save(User user);

    void deleteByUid(long userUid);

    User findByUid(long userUid);

    User findByUsername(String username);

    List<User> findAll();

    long count();
}
