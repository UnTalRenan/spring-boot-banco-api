package com.spring.banco.repository;

import com.spring.banco.entity.BalanceTransactions;
import com.spring.banco.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceTransactionsRepository extends JpaRepository<BalanceTransactions,Integer> {
    List<BalanceTransactions> findByClient(Client client);
}
