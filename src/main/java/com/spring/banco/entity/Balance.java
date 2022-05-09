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
@Table(name="BALANCE")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Temporal(TemporalType.DATE)
    Date creadoEn=new Date();

    @Column(unique = true,nullable = false)
    String account;

    BigDecimal balance;


    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;
}
