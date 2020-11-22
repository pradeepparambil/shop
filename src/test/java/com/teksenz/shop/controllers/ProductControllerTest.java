package com.teksenz.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teksenz.shop.domain.Product;
import com.teksenz.shop.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {
    private final String baseUrl = "/api/v1/products/";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    ProductService productService;

    Product getProduct() {
        return Product.builder()
                .name("Sharpie")
                .description("Highligter pen")
                .price(1.99F)
                .build();
    }


    @Test
    void findAllProducts() throws Exception {
        Product product = getProduct();
        product.setId(UUID.randomUUID());
        given(productService.findAll()).willReturn(List.of(product));
        mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk());
    }

    @Test
    void findAProduct() throws Exception {
        Product product = getProduct();
        product.setId(UUID.randomUUID());
        given(productService.findById(any())).willReturn(product);
        mockMvc.perform(get(baseUrl+product.getId()))
                .andExpect(status().isOk());
    }

    void saveNewProduct() throws Exception {
        Product product = getProduct();
        product.setId(UUID.randomUUID());
        given(productService.saveNewProduct(any())).willReturn(product);
        mockMvc.perform(post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getProduct())))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",baseUrl+product.getId()))
                .andReturn();

    }


    void updateProduct() throws Exception {
        Product product = getProduct();
        product.setId(UUID.randomUUID());
        given(productService.findById(any())).willReturn(product);
        mockMvc.perform(put(baseUrl+product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isNoContent());
    }


    void deleteById() throws Exception {
        Product product = getProduct();
        product.setId(UUID.randomUUID());
        given(productService.findById(any())).willReturn(product);
        mockMvc.perform(delete(baseUrl+product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}