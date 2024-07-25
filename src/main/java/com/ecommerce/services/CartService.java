package com.ecommerce.services;

import com.ecommerce.entities.Cart;
import com.ecommerce.entities.Client;
import com.ecommerce.entities.Product;
import com.ecommerce.repositories.CartRepository;
import com.ecommerce.repositories.ClientRepository;
import com.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository repository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProductRepository productRepository;


    public Cart addProduct(Integer clientId, Integer productId, Integer num) {
        Optional<Client> client = clientRepository.findById(clientId);
        Optional<Product> product = productRepository.findById(productId);
        if (client.isPresent() & product.isPresent()) {
            Cart cart = new Cart();
            cart.setClient_id(client.get());
            cart.setProduct_id(product.get());
            cart.setPrice(product.get().getPrice());
            cart.setAmount(num);
            cart.setDelivered(false);
            return repository.save(cart);
        } else {
            throw new RuntimeException("Client or Product not found");
        }
    }

    public Cart deleteOne(Integer cartId) {
        Optional<Cart> cart = repository.findById(cartId);
        if (cart.isPresent()) {
            repository.deleteById(cartId);
            return cart.get();
        } else {
            throw new RuntimeException("Cart not found");
        }
    }

    public List<Cart> findByClientIdAndDelivered (Integer clientId) {
        List<Cart> carts = repository.findByClientIdAndDelivered(clientId, false);
        if(carts.isEmpty()) {
            throw new RuntimeException("Cart not found");
        } else {
            return carts;
        }
    }
}