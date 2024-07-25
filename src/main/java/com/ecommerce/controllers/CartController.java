package com.ecommerce.controllers;

import com.ecommerce.entities.Cart;
import com.ecommerce.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/carts")
@Tag(name = "cart routes", description = "CRUD of cart")
public class CartController {

    @Autowired private CartService service;


    @PostMapping("/{client_id}/{productId}/{num}/")
    @Operation(summary = "Add product to cart", description = "Adds a product with a specified amount to the client's cart")
    @ApiResponse(responseCode = "200", description = "Product added to cart successfully")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Client or Product not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Cart> addProducts(@PathVariable Integer client_id, @PathVariable Integer productId, @PathVariable Integer num ) {
        try {
            Cart cart = service.addProduct(client_id, productId, num);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e){
            // System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{cartId}")
    @Operation(summary = "Remove product", description = "Removes a product from the client's cart")
    @ApiResponse(responseCode = "204", description = "Product removed from cart successfully")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Cart not found")
    @ApiResponse(responseCode = "404", description = "Internal server error")
    public ResponseEntity<Cart> deleteOne(@PathVariable Integer cartId){
        try{
            service.deleteOne(cartId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e){
            // System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{client_id}/")
    @Operation(summary = "Finds cart by ID and delivered status", description = "Returns a list of carts for a given client ID with ")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of carts")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Carts not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<Cart>> findByClientIdAndDelivered(@PathVariable Integer client_id){
        try {
            List<Cart> carts = service.findByClientIdAndDelivered(client_id);
            return ResponseEntity.ok(carts);
        } catch (RuntimeException e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
