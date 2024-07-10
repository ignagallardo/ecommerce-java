package com.ecommerce.controllers;

import com.ecommerce.entities.Invoice;
import com.ecommerce.entities.Product;
import com.ecommerce.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/invoices")
public class InvoiceController {

    @Autowired private InvoiceService service;

    @PostMapping
    public ResponseEntity<Invoice> create(@RequestBody Invoice invoice) {
        try {
            Invoice newInvoice = service.save(invoice);
            return new ResponseEntity<>(newInvoice, HttpStatus.CREATED);
        } catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> read(@PathVariable Integer id) {
        try {
            Optional<Invoice> invoice = service.readOne(id);
            if (invoice.isPresent()) {
                return ResponseEntity.ok(invoice.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
