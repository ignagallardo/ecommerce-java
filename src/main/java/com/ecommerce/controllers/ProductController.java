package com.ecommerce.controllers;

import com.ecommerce.entities.Cart;
import com.ecommerce.entities.Client;
import com.ecommerce.entities.Product;
import com.ecommerce.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @Autowired private ProductService service;

    @PostMapping
    @Operation(summary = "Create a product", description = "Creates a product")
    @ApiResponse(responseCode = "200", description = "Product created successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal serve error")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        try {
            Product newProduct = service.save(product);
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    @Operation(summary = "Read all products", description = "Reads all the products")
    @ApiResponse(responseCode = "200", description = "Products read successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal serve error")
    public ResponseEntity<List<Product>> readAll() {
        try {
            List<Product> products = service.readAll();
            return ResponseEntity.ok(products);
        } catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Read a product", description = "Reads an specified product")
    @ApiResponse(responseCode = "200", description = "Product read successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "500", description = "Internal serve error")
    public ResponseEntity<Product> readOne(@PathVariable Integer id) {
        try {
            Optional<Product> product = service.readOne(id);
            if (product.isPresent()) {
                return ResponseEntity.ok(product.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/")
    @Operation(summary = "Update a product", description = "Updates the information of an specified product")
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "500", description = "Internal serve error")
    public ResponseEntity<Product> update(@PathVariable Integer id, @RequestBody Product data) {
        try {
            Optional<Product> optionalProduct = service.readOne(id);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                if (data.getName() != null) {
                    product.setName(data.getName());
                }
                if (data.getDescription() != null) {
                    product.setDescription(data.getDescription());
                }
                if (data.getStock() != null) {
                    product.setStock(data.getStock());
                }
                if (data.getCarts() != null) {
                    product.setCarts(data.getCarts());
                }
                service.save(product);
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
