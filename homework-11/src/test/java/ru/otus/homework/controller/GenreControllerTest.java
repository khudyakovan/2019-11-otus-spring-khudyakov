package ru.otus.homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.otus.homework.controllers.GenreController;
import ru.otus.homework.repositories.GenreRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final String BASE_URL = "/api/v1/genres";

    @MockBean
    private GenreRepository genreRepository;

    @Test
    void shouldGetGenres() throws Exception{
        webTestClient.get().uri(BASE_URL)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);
    }
}
