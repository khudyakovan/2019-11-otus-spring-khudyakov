package ru.otus.graduation.repository.master;

import ru.otus.graduation.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> getRandomProducts();
}
