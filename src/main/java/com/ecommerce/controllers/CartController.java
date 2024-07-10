package com.ecommerce.controllers;

import com.ecommerce.entities.Cart;
import com.ecommerce.services.CartService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/carts")
public class CartController {

    @Autowired private CartService service;
    @Qualifier("resourceHandlerMapping")
    @Autowired
    private HandlerMapping resourceHandlerMapping;

    @PostMapping
    public ResponseEntity<Cart> create(@RequestBody Cart cart) {
        try {
            Cart newCart = service.save(cart);
            return new ResponseEntity<>(newCart, HttpStatus.CREATED);
        } catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Cart>> readAll() {
        try {
            List<Cart> carts = service.readAll();
            return ResponseEntity.ok(carts);
        } catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> read(@PathVariable Integer id) {
        try {
            Optional<Cart> pet = service.readOne(id);
            if (pet.isPresent()) {
                return ResponseEntity.ok(pet.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cart> update(@PathVariable Integer id, @RequestBody Cart data) {
        try {
            Optional<Cart> optionalCart = service.readOne(id);
            if (optionalCart.isPresent()) {
                Cart cart = optionalCart.get();
                if (data.getPrice() != null) {
                    cart.setPrice(data.getPrice());
                }
                if (data.getAmount() != null) {
                    cart.setAmount(data.getAmount());
                }
                if(data.getProduct_id() != null) {
                    cart.setProduct_id(data.getProduct_id());
                }
                if(data.getClient_id() != null) {
                    cart.setClient_id(data.getClient_id());
                }
                service.save(cart);
                return ResponseEntity.ok(cart);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cart> destroy(@PathVariable Integer id) {
        try {
            Optional<Cart> cart = service.destroyOne(id);
            if (cart.isPresent()) {
                return ResponseEntity.ok(cart.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{cartId}/{clientId}/addProducts/{productId}/{num}/")
    public ResponseEntity<Cart> addProducts(@PathVariable Integer cartId, @PathVariable Integer clientId, @PathVariable Integer productId, @PathVariable Integer num ) {
        try {
            Optional<Cart> cart = service.addProduct(cartId, clientId, productId, num);
            if (cart.isPresent()) {
                return ResponseEntity.ok(cart.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
