package com.example.onlineshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organization_registration_requests")
public class OrganizationRegistrationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "organization_name", unique = true)
    private String organizationName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private LocalDateTime date;

    @Column(name = "is_approved")
    private boolean isApproved;

    @PrePersist
    private void init(){
        date = LocalDateTime.now();
    }
}
