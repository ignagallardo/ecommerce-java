package com.ecommerce.services;

import com.ecommerce.entities.Product;
import com.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired private ProductRepository repository;

    public Product save(Product product) {
        return repository.save(product);
    }

    public List<Product> readAll() {
        return repository.findAll();
    }

    public Optional<Product> readOne(Integer id) {
        return repository.findById(id);
    }

}
