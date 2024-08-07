package com.ecommerce.services;

import com.ecommerce.entities.Cart;
import com.ecommerce.entities.Client;
import com.ecommerce.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired private ClientRepository repository;

    public Client save(Client client){
        return repository.save(client);
    }

    public Optional<Client> readOne (Integer id) {
        return repository.findById(id);
    }
}
