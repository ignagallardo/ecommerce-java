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

    public Cart save(Cart cart) {
        return repository.save(cart);
    }

    public List<Cart> readAll() {
        return repository.findAll();
    }

    public Optional<Cart> readOne(Integer id) {
        return repository.findById(id);
    }

    public Optional<Cart> destroyOne(Integer id) {
        Optional<Cart> cart = repository.findById(id);
        if (cart.isPresent()) {
            repository.deleteById(id);
        }
        return cart;
    }

    public Optional<Cart> addProduct(Integer cartId, Integer clientId, Integer productId, Integer num) {
        Optional<Cart> cart = repository.findById(cartId);
        Optional<Client> client = clientRepository.findById(clientId);
        Optional<Product> product = productRepository.findById(productId);
        if (cart.isEmpty() || client.isEmpty() || product.isEmpty()) {
            return cart;
        }
        Cart foundCart = cart.get();
        Client foundClient = client.get();
        Product foundProduct = product.get();
        foundCart.setClient_id(foundClient);
        repository.save(foundCart);
        for (int i = 1; i <= num; i++) {
            foundCart.setProduct_id(foundProduct);
            repository.save(foundCart);
        }
        return cart;
    }

    public Optional<Cart> deleteOne(Integer cartId, Integer clientId, Integer productId, Integer num) {
        Optional<Cart> cart = repository.findById(cartId);
        Optional<Client> client = clientRepository.findById(clientId);
        Optional<Product> product = productRepository.findById(productId);
        if (cart.isEmpty() || client.isEmpty() || product.isEmpty()) {
            return cart;
        }

        Cart foundCart = cart.get();
        Client foundClient = client.get();
        Product foundProduct = product.get();
        // me aseguro de que el carrito pertenece al cliente correcto
        if (!foundCart.getClient_id().equals(foundClient)) {
            return cart;
        }
        // me aseguro de que el producto está en el carrito
        if (!foundCart.getProduct_id().equals(foundProduct)) {
            return cart;
        }
        for (int i = 1; i <= num; i++) {
            if (product.isPresent()) {
                productRepository.deleteById(productId);
            }
        }
        // guardo el carrito actualizado
        repository.save(foundCart);

        return cart;
    }
}