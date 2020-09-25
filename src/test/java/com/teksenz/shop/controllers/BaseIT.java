package com.teksenz.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teksenz.shop.domain.Product;
import com.teksenz.shop.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public abstract class BaseIT {
    final String baseUrl = "/api/v1/products/";

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    ProductService productService;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    Product getProduct() {
        return Product.builder()
                .name("Sharpie")
                .description("Highligter pen")
                .price(1.99F)
                .build();
    }
}
