package ru.otus.graduation.repository.master;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.graduation.model.Image;

public interface ImageRepository extends MongoRepository<Image, String> {
    Image findByProductId(String productId);
}
