package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.entity.mongo.RoleMongo;
import ru.otus.homework.entity.mysql.Role;

@Service
public class RoleService implements GenericService<RoleMongo, Role> {

    @Override
    public RoleMongo transform(Role entity) {
        return new RoleMongo(String.valueOf(entity.getUid()), entity.getRoleName());
    }
}
