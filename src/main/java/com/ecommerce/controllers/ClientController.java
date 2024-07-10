package com.ecommerce.controllers;

import com.ecommerce.entities.Cart;
import com.ecommerce.entities.Client;
import com.ecommerce.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/invoices")
public class ClientController {

    @Autowired private ClientService service;

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody Client client) {
        try {
            Client newClient= service.save(client);
            return new ResponseEntity<>(newClient, HttpStatus.CREATED);
        } catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Integer id, @RequestBody Client data) {
        try {
            Optional<Client> optionalClient = service.readOne(id);
            if (optionalClient.isPresent()) {
                Client client = optionalClient.get();
                if (data.getName() != null) {
                    client.setName(data.getName());
                }
                if (data.getSurname() != null) {
                    client.setSurname(data.getSurname());
                }
                if (data.getDocnumber() != null) {
                    client.setDocnumber(data.getDocnumber());
                }
                if (data.getCarts() != null) {
                    client.setCarts(data.getCarts());
                }
                if (data.getInvoices() != null) {
                    client.setInvoices(data.getInvoices());
                }
                service.save(client);
                return ResponseEntity.ok(client);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
