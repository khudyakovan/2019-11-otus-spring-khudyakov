package ru.otus.homework.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    private static final String COMMON_USER_1 = "ac1";
    private static final String COMMON_USER_3 = "ac3";

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
                .andExpect(content().string(containsStringIgnoringCase(ROLE_2)));
    }

    @DisplayName("Показать страницу пользователей под текущим пользователем. Должен вернуть только текущего пользователя")
    @WithMockUser(
            username = COMMON_USER_3
    )
    @Test
    void shouldNotGetUsersPage() throws Exception{
        mvc.perform(get(USERS_URL))
                .andExpect(status().isOk())
                .andExpect(content().string(containsStringIgnoringCase(COMMON_USER_3)));
    }

    //Проверка на уровне URL
    //Проверяются сценарии .antMatchers("/books/add", "/books/*/edit", "/books/*/delete").hasRole("ADMIN")

    @DisplayName("Показать страницу добавления книги под админом")
    @WithMockUser(
            username = ADMIN_USER,
            roles = {ROLE_1, ROLE_2}
    )
    @Test
    void shouldGetAddBookPage() throws Exception{
        mvc
                .perform(get(ADD_BOOK_URL))
                .andExpect(status().isOk());
    }

    @DisplayName("Доступ запрещен не админу")
    @WithMockUser(
            username = COMMON_USER_3
    )
    @Test
    void shouldDenyAddBookPage() throws Exception{
        mvc
                .perform(get(ADD_BOOK_URL))
                .andExpect(status().isForbidden());
    }

    @DisplayName("Добавление книги - редирект")
    @Test
    void shouldNotGetAddBookPageAndRedirectToLogin() throws Exception{
        mvc
                .perform(get(ADD_BOOK_URL))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("Показать страницу редактирования книги под админом")
    @WithMockUser(
            username = ADMIN_USER,
            roles = {ROLE_1, ROLE_2}
    )
    @Test
    void shouldGetEditBookPage() throws Exception{
        mvc
                .perform(get(EDIT_BOOK_URL))
                .andExpect(status().isOk());
    }

    @DisplayName("Доступ запрещен не админу")
    @WithMockUser(
            username = COMMON_USER_3
    )
    @Test
    void shouldDenyEditBookPage() throws Exception{
        mvc
                .perform(get(EDIT_BOOK_URL))
                .andExpect(status().isForbidden());
    }
    
    @DisplayName("Редактирование книги - редирект")
    @Test
    void shouldNotGetEditBookPageAndRedirectToLogin() throws Exception{
        mvc
                .perform(get(EDIT_BOOK_URL))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("Показать страницу удаления книги под админом")
    @WithMockUser(
            username = ADMIN_USER,
            roles = {ROLE_1, ROLE_2}
    )
    @Test
    void shouldGetDeleteBookPage() throws Exception{
        mvc
                .perform(get(DELETE_BOOK_URL))
                .andExpect(status().isOk());
    }

    @DisplayName("Доступ запрещен не админу")
    @WithMockUser(
            username = COMMON_USER_3
    )
    @Test
    void shouldDenyDeleteBookPage() throws Exception{
        mvc
                .perform(get(DELETE_BOOK_URL))
                .andExpect(status().isForbidden());
    }
    
    @DisplayName("Удаление книги - редирект")
    @Test
    void shouldNotGetDeleteBookPageAndRedirectToLogin() throws Exception{
        mvc
                .perform(get(DELETE_BOOK_URL))
                .andExpect(status().is3xxRedirection());
    }
}
