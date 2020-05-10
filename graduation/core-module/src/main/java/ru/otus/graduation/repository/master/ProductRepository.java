package ru.otus.graduation.repository.master;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.graduation.model.Product;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String>, ProductRepositoryCustom {

    List<Product> findByParentId(String parentId);
    List<Product> findByParentIdStartingWith(String level);
    List<Product> findByIdIsIn(List<String> ids);
}
