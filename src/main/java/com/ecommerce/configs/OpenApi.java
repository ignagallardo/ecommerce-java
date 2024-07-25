package com.ecommerce.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Ecommerce Api",
                version = "1.0",
                description = "CRUD of carts, clients, invoices and products"
        )
)
public class OpenApi {
}
