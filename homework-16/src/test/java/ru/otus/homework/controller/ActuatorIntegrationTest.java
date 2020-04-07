package ru.otus.homework.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.GenreService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Интеграционный тест Spring Actuator...")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ActuatorIntegrationTest {

    private static final String AUTHORS_HEALTH_SERVICE_URL = "/actuator/health/authorsService";
    private static final String GENRES_HEALTH_SERVICE_URL = "/actuator/health/genresService";
    private static final String BASE_METRICS_URL = "/actuator/metrics";
    private static final String NAMED_METRIC_URL = "/actuator/metrics/jvm.memory.max";
    private static final String LOGFILE_URL = "/actuator/logfile";
    private static final String STATUS_UP = "UP";
    private static final String STATUS_OOS = "OUT_OF_SERVICE";

    @Autowired
    private MockMvc mvc;

    @Autowired
    AuthorService authorService;
    @Autowired
    GenreService genreService;

    @DisplayName("... проверяет, что Custom Health Service AuthorsService в статусе UP")
    @Test
    @Order(1)
    void shouldTestIfAuthorHealthServiceUp() throws Exception {
        mvc.perform(get(AUTHORS_HEALTH_SERVICE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(STATUS_UP));
    }

    @DisplayName("... проверяет, что Custom Health Service GenresService в статусе UP")
    @Test
    @Order(2)
    void shouldTestIfGenreHealthServiceUp() throws Exception {
        mvc.perform(get(GENRES_HEALTH_SERVICE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(STATUS_UP));
    }

    @DisplayName("... проверяет, что Custom Health Service AuthorsService в статусе Out Of Service")
    @Test
    @Order(3)
    void shouldTestIfAuthorHealthServiceOutOfService() throws Exception {
        authorService.deleteAll();
        mvc.perform(get(AUTHORS_HEALTH_SERVICE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("status").value(STATUS_OOS));
    }

    @DisplayName("... проверяет, что Custom Health Service GenresService в статусе Out Of Service")
    @Test
    @Order(4)
    void shouldTestIfGenreHealthServiceOutOfService() throws Exception {
        genreService.deleteAll();
        mvc.perform(get(GENRES_HEALTH_SERVICE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("status").value(STATUS_OOS));
    }

    @DisplayName("... проверяет доступность metrics")
    @Test
    @Order(5)
    void shouldTestIfMetricsAreAvailable() throws Exception {
        mvc.perform(get(BASE_METRICS_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("... проверяет доступность metrics по имени")
    @Test
    @Order(6)
    void shouldTestIfMetricAreAvailableByName() throws Exception {
        mvc.perform(get(NAMED_METRIC_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("... проверяет доступность logfile")
    @Test
    @Order(7)
    void shouldTestIfLogfileIsAvailable() throws Exception {
        mvc.perform(get(LOGFILE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
