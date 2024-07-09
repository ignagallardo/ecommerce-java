package com.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "carts")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @Getter @Setter private Double price;
    @Getter @Setter private Integer amount;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @Getter @Setter private Client client_id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @Getter @Setter private Product product_id;
}
