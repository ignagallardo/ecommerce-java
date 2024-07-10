package com.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity @Table (name = "products")
@NoArgsConstructor @AllArgsConstructor @ToString @EqualsAndHashCode
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Integer id;

    @Getter @Setter private String name;
    @Getter @Setter private String description;
    @Getter @Setter private Integer stock;
    @Getter @Setter private double price;

    @OneToMany(mappedBy = "product_id", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter @Setter private List<Cart> carts;

}
