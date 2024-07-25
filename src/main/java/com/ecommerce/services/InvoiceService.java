package com.ecommerce.services;

import com.ecommerce.entities.Cart;
import com.ecommerce.entities.Client;
import com.ecommerce.entities.Invoice;
import com.ecommerce.repositories.CartRepository;
import com.ecommerce.repositories.ClientRepository;
import com.ecommerce.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Optional;


@Service
public class InvoiceService {

    @Autowired private InvoiceRepository repository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private CartRepository cartRepository;
    

    public Invoice generateInvoice(Integer clientId){
        Optional<Client> client = clientRepository.findById(clientId);
        if(client.isPresent()) {
            List<Cart> carts = cartRepository.findByClientIdAndDelivered(clientId, false);
            if(carts.isEmpty()) {
                throw new RuntimeException("Not products found in cart");
            } else {
                Client foundClientId = client.get();
                Invoice invoice = new Invoice();
                invoice.setClient_id(foundClientId);
                invoice.setCreated_at(new Date());
                double total = 0;
                for (Cart cart: carts) {
                    total += cart.getAmount()*cart.getPrice();
                    cart.setDelivered(true);
                }
                invoice.setTotal(total);
                return repository.save(invoice);
            }
        } else {
            throw new RuntimeException("Client not found");
        }
    }

    public Invoice getLastInvoicesByClientId(Integer clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        if(client.isPresent()) {
            List<Invoice> invoices = client.get().getInvoices();
            if(invoices.isEmpty()) {
                throw new RuntimeException("No invoices found for the client");
            }
            Invoice lastInvoice = invoices.get(invoices.size()-1);
            return lastInvoice;
        } else {
            throw new RuntimeException("Client not found");
        }
    }
}
