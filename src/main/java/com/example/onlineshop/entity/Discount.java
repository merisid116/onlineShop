package com.example.onlineshop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    @Column(name = "discount_amount")
    private Double discountAmount;

    @Column(name = "start_date")
    private LocalDateTime startDate = LocalDateTime.now();
/** время действия скидки 10 дней*/
    @Column(name = "end_date")
    private LocalDateTime endDate = startDate.plusDays(10);



}

