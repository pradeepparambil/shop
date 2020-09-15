package com.teksenz.shop.services;

import com.teksenz.shop.domain.Product;
import com.teksenz.shop.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface ProductService {
    Product saveNewProduct(Product product);
    List<Product> findAll();
    Product findById(UUID uuid);
    void updateById(UUID uuid, Product product);
    void deleteById(UUID uuid);

}
