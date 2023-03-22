package com.example.onlineshop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column
    private String username;

    @NonNull
    @Column(name = "email", unique = true)
    private String email;

    @Column
    @NonNull
    private String password;

    @Column
    private Double balance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Purchase> purchases;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_notification",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "notification_id")})
    private List <Notification> notifications;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrganizationRegistrationRequest> organizationRegistrationRequests;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductRegistrationRequest> productRegistrationRequests;

    @Column(name = "is_enabled")
    private Boolean isEnabled = true;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && username.equals(user.username) && email.equals(user.email) && password.equals(user.password) && Objects.equals(balance, user.balance) && Objects.equals(purchases, user.purchases) && Objects.equals(notifications, user.notifications) && Objects.equals(organizationRegistrationRequests, user.organizationRegistrationRequests) && Objects.equals(productRegistrationRequests, user.productRegistrationRequests) && Objects.equals(isEnabled, user.isEnabled) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, balance, purchases, notifications, organizationRegistrationRequests, productRegistrationRequests, isEnabled, role);
    }
}


