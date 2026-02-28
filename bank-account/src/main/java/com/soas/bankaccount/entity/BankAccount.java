package com.soas.bankaccount.entity;

import com.soas.api.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="bank_accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Double eur = 0.0;

    @Column(nullable = false)
    private Double usd = 0.0;

    @Column(nullable = false)
    private Double gbp = 0.0;

    @Column(nullable = false)
    private Double chf = 0.0;

    @Column(nullable = false)
    private Double rsd = 0.0;


}
