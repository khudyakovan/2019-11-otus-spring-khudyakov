package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.entity.mongo.CommentMongo;
import ru.otus.homework.entity.mongo.RoleMongo;
import ru.otus.homework.entity.mongo.UserMongo;
import ru.otus.homework.entity.mysql.Comment;
import ru.otus.homework.entity.mysql.Role;
import ru.otus.homework.entity.mysql.User;
import ru.otus.homework.repository.mysql.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements GenericService<UserMongo, User> {

    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final RoleService roleService;

    @Override
    public UserMongo transform(User entity) {
        Long id = entity.getId();

        return new UserMongo(String.valueOf(id),
                entity.getUsername(),
                entity.getPassword(),
                entity.getFirstName(),
                entity.getLastName(),
                this.populateComments(commentRepository.findCommentByUserId(entity.getId())),
                this.populateRoles(entity.getRoles()));
    }

    private List<CommentMongo> populateComments(List<Comment> comments) {
        List<CommentMongo> cm = new ArrayList<>();
        comments.forEach(comment -> {
            cm.add(commentService.transform(comment));
        });
        return cm;
    }

    private List<RoleMongo> populateRoles(List<Role> roles){
        List<RoleMongo> rm = new ArrayList<>();
        roles.forEach(role -> {
            rm.add(roleService.transform(role));
        });
        return rm;
    }
}
