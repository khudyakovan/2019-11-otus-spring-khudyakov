package ru.otus.graduation.catalogue.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.graduation.config.ApplicationConfig;
import ru.otus.graduation.repository.LevelRepository;
import ru.otus.graduation.repository.ProductRepository;
import ru.otus.graduation.service.HelperService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {CatalogController.class, ApplicationConfig.class})
class CatalogControllerTest {

    private static final String BASE_URL = "/api/v1";

    @Autowired
    private MockMvc mvc;
    @MockBean
    private LevelRepository levelRepository;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private MongoTemplate mongoTemplate;
    @MockBean
    private HelperService helperService;

    @Test
    void getHierarchyRootLevels() throws Exception {
        mvc.perform(get(BASE_URL + "/levels")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getHierarchyLevelsByParentId() throws Exception {
        mvc.perform(get(BASE_URL + "/levels/levels")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getGoodsByHierarchyLevel() throws Exception {
        mvc.perform(get(BASE_URL + "/goods/goods")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getShowcase() throws Exception {
        mvc.perform(get(BASE_URL + "/goods/showcase")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getShopId() throws Exception {
        mvc.perform(get(BASE_URL + "/shop/id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
