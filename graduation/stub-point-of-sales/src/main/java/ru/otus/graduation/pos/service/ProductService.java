package ru.otus.graduation.pos.service;

import ru.otus.graduation.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findByIdIsIn(List<String> ids);
}
