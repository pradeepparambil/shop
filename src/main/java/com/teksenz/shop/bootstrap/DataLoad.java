package com.teksenz.shop.bootstrap;

import com.teksenz.shop.domain.Product;
import com.teksenz.shop.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class DataLoad implements CommandLineRunner {

    @Autowired
    private final ProductRepository productRepository;
    @Override
    public void run(String... args) throws Exception {
        if(productRepository.count()==0){
            loadProducts();
        }

    }

    private void loadProducts() {
        Product product1 = Product.builder()
                .name("Thermos Vacuum Insulated Spiderman FUNTainer Bottle, 355 ml")
                .description("Spiderman FUNTainer Bottle")
                .price(16.97F)
                .build();
        Product product2 = Product.builder()
                .name("Hamilton Beach 4 Slice Toaster Oven 31401C")
                .description("Hamilton Beach")
                .price(16.97F)
                .build();
        Product product3 = Product.builder()
                .name("TruBlue The Original Backpack - Redwood")
                .description("With padded laptop compartment")
                .price(39.96F)
                .build();
        Product product4 = Product.builder()
                .name("Blue Whale Striped Pillow")
                .description("Blue Whale")
                .price(4.97F)
                .build();
        Product product5 = Product.builder()
                .name("ViscoLogic INFINITY Ergonomic Gaming Racing Height Adjustable Swivel Home Office Computer Desk Chair")
                .description("This sporty racing seat impresses not only through exclusive design but also through premium " +
                        "quality workmanship.It has bucket seat's typical shape which provides excellent support. " +
                        "The low-maintenance artificial leather makes it easier to clean. ")
                .price(39.96F)
                .build();
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        log.info("Number of products loaded via bootstrap - "+ productRepository.count());


    }
}
