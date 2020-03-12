package ru.otus.homework.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mvc;

    private static final String USERS_URL = "/users";
    private static final String USERS_VIEW = "users";
    private static final String ADD_BOOK_URL = "/books/add";
    private static final String EDIT_BOOK_URL = "/books/15/edit";
    private static final String DELETE_BOOK_URL = "/books/15/delete";
    private static final String ADMIN_USER = "admin";
    private static final String ROLE_1 = "USER";
    private static final String ROLE_2 = "ADMIN";
    private static final String COMMON_USER = "ac1";

    //Проверка авторизации на уровне доменных сущностей

    @DisplayName("Показать страницу пользователей со всеми пользователями под админом")
    @WithMockUser(
            username = ADMIN_USER,
            roles = {ROLE_1, ROLE_2}
    )
    @Test
    void shouldGetUsersPage() throws Exception {
        mvc.perform(get(USERS_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(USERS_VIEW))
                .andExpect(content().string(containsStringIgnoringCase(ADMIN_USER)))
                .andExpect(content().string(containsStringIgnoringCase(COMMON_USER)));
    }

    @DisplayName("Показать страницу пользователей под текущим пользователем. Должен вернуть только текущего пользователя")
    @WithMockUser(
            username = COMMON_USER
    )
    @Test
    void shouldNotGetUsersPage() throws Exception {
        mvc.perform(get(USERS_URL))
                .andExpect(status().isOk())
                .andExpect(content().string(containsStringIgnoringCase(COMMON_USER)))
                .andExpect(content().string(Matchers.not(containsStringIgnoringCase(ADMIN_USER))));
    }

    //Проверка на уровне URL
    //Проверяются сценарии .antMatchers("/books/add", "/books/*/edit", "/books/*/delete").hasRole("ADMIN")

    @DisplayName("Показать страницу добавления книги под админом")
    @WithMockUser(
            username = ADMIN_USER,
            roles = {ROLE_1, ROLE_2}
    )
    @ParameterizedTest
    @ValueSource(strings = {ADD_BOOK_URL, EDIT_BOOK_URL, DELETE_BOOK_URL})
    void shouldShowProtectedPages(String url) throws Exception {
        mvc
                .perform(get(url))
                .andExpect(status().isOk());
    }

    @DisplayName("Доступ запрещен не админу")
    @WithMockUser(
            username = COMMON_USER
    )
    @ParameterizedTest
    @ValueSource(strings = {ADD_BOOK_URL, EDIT_BOOK_URL, DELETE_BOOK_URL})
    void shouldNotShowForbiddenPages(String url) throws Exception {
        mvc
                .perform(get(url))
                .andExpect(status().isForbidden());
    }

    @DisplayName("Вход на защищенные страницы - редирект на авторизацию")
    @ParameterizedTest
    @ValueSource(strings = {ADD_BOOK_URL, EDIT_BOOK_URL, DELETE_BOOK_URL})
    void shouldNotShowForbiddenPagesAndRedirectToLogin(String url) throws Exception {
        mvc
                .perform(get(url))
                .andExpect(status().is3xxRedirection());
    }
}
