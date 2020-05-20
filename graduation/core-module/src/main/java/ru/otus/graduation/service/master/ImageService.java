package ru.otus.graduation.service.master;

import ru.otus.graduation.model.Image;

public interface ImageService {
    Image findByProductId(String productId);
}
