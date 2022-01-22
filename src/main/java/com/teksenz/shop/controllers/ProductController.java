package com.teksenz.shop.controllers;

import com.teksenz.shop.domain.Product;
import com.teksenz.shop.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Fetch all records from DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fetched all records successfully",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping({"","/"})
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findAllProducts(){
        return productService.findAll();
    }


    @Operation(summary = "Fetch the record by the specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fetched the record successfully",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> findAProduct(@PathVariable UUID id){
        return new ResponseEntity<>(productService.findById(id),HttpStatus.OK) ;
    }


    @Operation(summary = "Saves a new record to DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Saved a new record successfully",
                    headers = {@Header(name = "Location",description = "Location of the resource created")}),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping({"","/"})
    public ResponseEntity saveNewProduct(@RequestBody Product product){
        Product savedProduct = productService.saveNewProduct(product);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/api/v1/products/" + savedProduct.getId());
        return new ResponseEntity(httpHeaders,HttpStatus.CREATED);

    }

    @Operation(summary = "Update an existing record in DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Updated the record successfully"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable UUID id, @RequestBody Product product){
        productService.updateById(id,product);
    }

    @Operation(summary = "Delete a record from DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Record deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id){
        productService.deleteById(id);
    }

}
