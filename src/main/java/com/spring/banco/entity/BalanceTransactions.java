package com.spring.banco.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="BALANCE_TRANSACTIONS")
public class BalanceTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Temporal(TemporalType.DATE)
    Date creadoEn=new Date();
    Integer account;
    BigDecimal amount;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;
}
