package ru.otus.graduation.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.graduation.model.User;
import ru.otus.graduation.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        User u = userRepository.findByMobilePhone(user.getMobilePhone());
        if (u != null) {
            user.setId(u.getId());
        }
        return userRepository.save(user);
    }
}
