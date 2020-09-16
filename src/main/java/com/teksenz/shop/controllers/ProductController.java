package com.teksenz.shop.controllers;

import com.teksenz.shop.domain.Product;
import com.teksenz.shop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/products", produces = {"application/json"})
public class ProductController {
    @Autowired
    private final ProductService productService;

    @GetMapping({"","/"})
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findAllProducts(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findAProduct(@PathVariable UUID id){
        return new ResponseEntity<>(productService.findById(id),HttpStatus.OK) ;
    }
    @PostMapping({"","/"})
    public ResponseEntity saveNewProduct(@RequestBody Product product){
        Product savedProduct = productService.saveNewProduct(product);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/api/v1/products/" + savedProduct.getId());
        return new ResponseEntity(httpHeaders,HttpStatus.CREATED);

    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable UUID id, @RequestBody Product product){
        productService.updateById(id,product);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id){
        productService.deleteById(id);
    }

}
