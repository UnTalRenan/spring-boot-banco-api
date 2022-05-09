package com.spring.banco.repository;

import com.spring.banco.entity.Balance;
import com.spring.banco.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance,Integer> {
    public Balance findByaccount(String account);

    public List<Balance> findByClient(Client client);
}
