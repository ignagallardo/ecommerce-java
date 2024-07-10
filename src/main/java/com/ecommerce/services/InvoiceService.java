package com.ecommerce.services;

import com.ecommerce.entities.Client;
import com.ecommerce.entities.Invoice;
import com.ecommerce.repositories.ClientRepository;
import com.ecommerce.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired private InvoiceRepository repository;

    public Optional<Invoice> readOne(Integer id){
        return repository.findById(id);
    }

    public Invoice save(Invoice invoice){
        return repository.save(invoice);
    }
}
