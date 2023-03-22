package com.example.onlineshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "name", unique = true)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Lob
    @Column(columnDefinition="BLOB")
    private byte[] logo;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductRegistrationRequest> registrationRequests;

    @Column(name = "is_enabled")
    private Boolean isEnabled = true;
}

