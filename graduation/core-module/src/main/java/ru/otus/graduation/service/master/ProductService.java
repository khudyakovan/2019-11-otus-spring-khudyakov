package ru.otus.graduation.service.master;

import ru.otus.graduation.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findByIdIsIn(List<String> ids);
    List<Product> findByParentId(String parentId);
    List<Product> findByParentIdStartingWith(String level);
    List<Product> getRandomProducts();
}
