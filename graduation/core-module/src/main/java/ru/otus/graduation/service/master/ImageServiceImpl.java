package ru.otus.graduation.service.master;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.graduation.model.Image;
import ru.otus.graduation.repository.master.ImageRepository;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public Image findByProductId(String productId) {
        return imageRepository.findByProductId(productId);
    }
}
