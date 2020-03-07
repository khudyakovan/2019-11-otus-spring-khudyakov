package ru.otus.homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.otus.homework.controllers.AuthorController;
import ru.otus.homework.repositories.AuthorRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(AuthorController.class)
class AuthorControllerTest {

    private static final String BASE_URL = "/api/v1/authors";

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AuthorRepository authorRepository;


    @Test
    void shouldGetAuthors() throws Exception{
        webTestClient.get().uri(BASE_URL)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);
    }
}
