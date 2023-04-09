package ru.clevertec.ecl.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="certificate_id", nullable=false)
    private GiftCertificate certificate;

    @Column(name = "purchase_time", nullable=false)
    private LocalDateTime purchaseTime;

    @Column(name="price")
    private BigDecimal totalPrice;



}
