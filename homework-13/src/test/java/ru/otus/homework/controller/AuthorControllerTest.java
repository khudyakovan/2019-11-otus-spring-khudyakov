package ru.otus.homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(value = AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private UserService userService;
    @MockBean
    private AuthorService authorService;

    private static final String AUTHORS_URL = "/authors.html";
    private static final String AUTHORS_VIEW = "authors";
    private static final String USERNAME = "admin";
    private static final String ROLE_1 = "USER";
    private static final String ROLE_2 = "ADMIN";

    @Test
    void shouldNotRedirectToLogin() throws Exception {
        mvc.perform(get(AUTHORS_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(AUTHORS_VIEW));
    }

    @WithMockUser(
            username = USERNAME,
            roles = {ROLE_1, ROLE_2}
    )
    @Test
    void shouldGetAuthorsPage() throws Exception {
        mvc.perform(get(AUTHORS_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(AUTHORS_VIEW));
    }
}
