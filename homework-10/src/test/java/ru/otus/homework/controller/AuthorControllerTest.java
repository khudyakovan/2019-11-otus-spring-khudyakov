package ru.otus.homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AuthorController.class)
class AuthorControllerTest {

    private static final String BASE_URL = "/api/v1/authors";

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthorController authorController;

    @Test
    void shouldGetAuthors() throws Exception{
        mvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
