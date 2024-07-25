package com.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Entity @Table(name = "carts")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Integer id;

    @Getter @Setter private Double price;
    @Getter @Setter private Integer amount;
    @Getter @Setter private boolean delivered;

    @ManyToOne
    @JoinColumn(name = "client", nullable = false)
    @Getter @Setter private Client client;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @Getter @Setter private Product product_id;

}
