package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.homework.entity.User;
import ru.otus.homework.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public String usersPage(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }
}
