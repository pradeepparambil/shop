package com.teksenz.shop.controllers;

import com.teksenz.shop.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest//(controllers = ProductController.class)
public class ProductControllerSecurityTest extends BaseIT{


    @Test
    void findAllProducts() throws Exception {
        Product product = getProduct();
        product.setId(UUID.randomUUID());
        given(productService.findAll()).willReturn(List.of(product));

        mockMvc.perform(get(baseUrl+product.getId()).with(httpBasic("john","john123")))
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

    @Test
    void saveNewProductByStoreManager() throws Exception {
        Product product = getProduct();
        product.setId(UUID.randomUUID());
        given(productService.saveNewProduct(any())).willReturn(product);
        mockMvc.perform(post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getProduct()))
                .with(httpBasic("maria","maria123")))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",baseUrl+product.getId()))
                .andReturn();

    }
    @Test
    void saveNewProductBySalesPerson() throws Exception {
        Product product = getProduct();
        product.setId(UUID.randomUUID());
        given(productService.saveNewProduct(any())).willReturn(product);
        mockMvc.perform(post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getProduct()))
                .with(httpBasic("john","john123")))
                .andExpect(status().isForbidden())
                .andReturn();

    }

    @Test
    void updateProduct() throws Exception {
        Product product = getProduct();
        product.setId(UUID.randomUUID());
        given(productService.findById(any())).willReturn(product);
        mockMvc.perform(put(baseUrl+product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)).with(httpBasic("maria","maria123")))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteById() throws Exception {
        Product product = getProduct();
        product.setId(UUID.randomUUID());
        given(productService.findById(any())).willReturn(product);
        mockMvc.perform(delete(baseUrl+product.getId())
                .with(httpBasic("maria","maria123"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    @Test
    void deleteByIdBySale() throws Exception {
        Product product = getProduct();
        product.setId(UUID.randomUUID());
        given(productService.findById(any())).willReturn(product);
        mockMvc.perform(delete(baseUrl+product.getId())
                .with(httpBasic("john","john123"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


}
