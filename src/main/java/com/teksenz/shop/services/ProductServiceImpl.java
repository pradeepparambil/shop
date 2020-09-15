package com.teksenz.shop.services;

import com.teksenz.shop.domain.Product;
import com.teksenz.shop.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;

    @Override
    public Product saveNewProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(UUID uuid) {
        return productRepository.findById(uuid).orElseThrow(()->{
            throw new RuntimeException("Product not found");
        });
    }

    @Override
    public void updateById(UUID uuid, Product product) {
        findById(uuid);
        product.setId(uuid);
        productRepository.save(product);

    }

    @Override
    public void deleteById(UUID uuid) {
        findById(uuid);
        productRepository.deleteById(uuid);

    }


}
