package ru.otus.homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.service.GenreService;
import ru.otus.homework.service.UserDetailsServiceImpl;
import ru.otus.homework.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(value = GenreController.class)
public class GenreControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private UserService userService;
    @MockBean
    private GenreService genreService;

    private static final String GENRES_URL = "/genres.html";
    private static final String GENRES_VIEW = "genres";
    private static final String USERNAME = "admin";
    private static final String ROLE_1 = "USER";
    private static final String ROLE_2 = "ADMIN";


    @Test
    void shouldRedirectToLogin() throws Exception {
        mvc.perform(get(GENRES_URL))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(
            username = USERNAME,
            roles = {ROLE_1, ROLE_2}
    )
    @Test
    void shouldGetGenresPage() throws Exception {
        mvc.perform(get(GENRES_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(GENRES_VIEW));
    }
}
