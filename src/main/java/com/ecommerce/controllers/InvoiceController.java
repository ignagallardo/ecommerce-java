package com.ecommerce.controllers;

import com.ecommerce.entities.Invoice;
import com.ecommerce.entities.Product;
import com.ecommerce.services.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Generate an invoice", description = "Generates an invoice for an specified client with the total amount")
    @ApiResponse(responseCode = "200", description = "Invoice generated successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Client or carts not found")
    @ApiResponse(responseCode = "500", description = "Internal serve error")
    public ResponseEntity<Invoice> generateInvoice(@RequestParam Integer clientId) {
        try {
            Invoice newInvoice = service.generateInvoice(clientId);
            return ResponseEntity.status(HttpStatus.CREATED).body(newInvoice);
        } catch(RuntimeException e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{clientId}/")
    @Operation(summary = "Get last invoice of a client", description = "Gets the last invoice of a specified client")
    @ApiResponse(responseCode = "200", description = "Invoice retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Client not found")
    @ApiResponse(responseCode = "500", description = "Internal serve error")
    public ResponseEntity<Invoice> getLastInvoiceById(@PathVariable Integer clientId) {
        try {
            Invoice invoice = service.getLastInvoicesByClientId(clientId);
            return ResponseEntity.ok(invoice);
        } catch(RuntimeException e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
