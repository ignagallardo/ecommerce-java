package com.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity @Table(name = "clients")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Integer id;

    @Getter @Setter private String name;
    @Getter @Setter private String surname;
    @Getter @Setter private Integer docnumber;

    @OneToMany(mappedBy = "client_id", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter @Setter private List<Cart> carts;

    @OneToMany(mappedBy = "client_id", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter @Setter private List<Invoice> invoices;
}
