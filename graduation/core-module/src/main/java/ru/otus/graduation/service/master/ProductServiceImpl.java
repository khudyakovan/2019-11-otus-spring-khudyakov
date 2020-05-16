package ru.otus.graduation.service.master;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.graduation.model.Product;
import ru.otus.graduation.repository.master.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public List<Product> findByIdIsIn(List<String> ids) {
        return productRepository.findByIdIsIn(ids);
    }

    @Override
    public List<Product> findByParentId(String parentId) {
        return productRepository.findByParentId(parentId);
    }

    @Override
    public List<Product> findByParentIdStartingWith(String level) {
        return productRepository.findByParentIdStartingWith(level);
    }

    @Override
    public List<Product> getRandomProducts() {
        return productRepository.getRandomProducts();
    }
}
