package ru.otus.homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    private static final String USERS_URL = "/users";
    private static final String USERS_VIEW = "users";
    private static final String USERNAME = "admin";
    private static final String ROLE_1 = "USER";
    private static final String ROLE_2 = "ADMIN";

    @WithMockUser(
            username = USERNAME,
            roles = {ROLE_1, ROLE_2}
    )
    @Test
    void shouldGetUsersPage() throws Exception {
        mvc.perform(get(USERS_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(USERS_VIEW));
    }
}
