package ru.otus.graduation.catalogue.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.graduation.catalogue.service.CheckoutEmitterService;
import ru.otus.graduation.config.ApplicationConfig;
import ru.otus.graduation.service.HelperService;
import ru.otus.graduation.service.master.LevelService;
import ru.otus.graduation.service.master.ProductService;
import ru.otus.graduation.service.proposal.ProposalService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import({CatalogController.class})
class CatalogControllerTest {

    private static final String BASE_URL = "/api/v1/catalog";

    @Autowired
    private MockMvc mvc;
    @MockBean
    private LevelService levelService;
    @MockBean
    private  ApplicationConfig applicationConfig;
    @MockBean
    private ProductService productService;
    @MockBean
    private ProposalService proposalService;
    @MockBean
    private CheckoutEmitterService checkoutEmitterService;
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
