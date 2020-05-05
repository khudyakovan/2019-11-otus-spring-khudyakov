package ru.otus.graduation.catalogue.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.graduation.config.ApplicationConfig;
import ru.otus.graduation.model.Level;
import ru.otus.graduation.model.Product;
import ru.otus.graduation.repository.LevelRepository;
import ru.otus.graduation.repository.ProductRepository;
import ru.otus.graduation.service.HelperService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CatalogController {

    private final ApplicationConfig applicationConfig;
    private final LevelRepository levelRepository;
    private final ProductRepository productRepository;
    private final HelperService helperService;
    private static final String API_PREFIX = "/api/v1";

    @GetMapping(value = {API_PREFIX + "/shop/id"})
    public String getShopId(){
        return applicationConfig.getShopId();
    }

    @GetMapping(value = {API_PREFIX + "/levels"})
    public List<Level> getHierarchyRootLevels(){
        return levelRepository.findByParentId("null");
    }

    @GetMapping(value = {API_PREFIX + "/levels/{id}"})
    public Level getHierarchyLevelById(@PathVariable("id") String id){
        return levelRepository.findById(id).get();
    }

    @GetMapping(value = {API_PREFIX + "/levels/parent/{parentId}"})
    public List<Level> getHierarchyLevelsByParentId(@PathVariable("parentId") String parentId){
        return levelRepository.findByParentId(parentId);
    }

    @GetMapping(value = {API_PREFIX + "/goods/{parentId}"})
    public List<Product> getGoodsByHierarchyLevel(@PathVariable("parentId") String parentId){
        parentId = helperService.getBeginningOfLevel(parentId);
        return productRepository.findByParentIdStartingWith(parentId);
    }

    @GetMapping(value = {API_PREFIX + "/goods/showcase"})
    public List<Product> getShowcase(){
        return productRepository.getRandomProducts();
    }
}
