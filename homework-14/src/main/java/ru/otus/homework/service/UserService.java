package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.homework.entity.mongo.RoleMongo;
import ru.otus.homework.entity.mongo.UserMongo;
import ru.otus.homework.entity.mysql.Role;
import ru.otus.homework.entity.mysql.User;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements GenericService<UserMongo, User> {

    private final RoleService roleService;

    @Override
    public UserMongo transform(User entity) {
        return new UserMongo(String.valueOf(new ObjectId()),
                entity.getUsername(),
                entity.getPassword(),
                entity.getFirstName(),
                entity.getLastName(),
                this.populateRoles(entity.getRoles()));
    }

    private List<RoleMongo> populateRoles(List<Role> roles){
        List<RoleMongo> rm = new ArrayList<>();
        roles.forEach(role -> {
            rm.add(roleService.transform(role));
        });
        return rm;
    }
}
