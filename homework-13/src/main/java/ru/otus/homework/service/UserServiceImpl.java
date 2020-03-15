package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.entity.User;
import ru.otus.homework.repository.ObjectNotFoundException;
import ru.otus.homework.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    ApplicationProperties applicationProperties;

    @Override
    public User save(User user) {

        return userRepository.save(user);
    }

    @Override
    public void deleteByUid(long commentatorUid) {
        userRepository.deleteById(commentatorUid);
    }

    @Override
    public User findByUid(long commentatorUid) {
        return userRepository.findById(commentatorUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), commentatorUid)));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(new User("","","",""));
    }

    @Override
    public List<User> findAll() {

        return userRepository.findAll();
    }

    @Override
    public long count() {

        return userRepository.count();
    }
}
