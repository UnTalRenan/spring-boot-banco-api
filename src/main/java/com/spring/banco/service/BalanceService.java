package com.spring.banco.service;

import com.spring.banco.dto.Respuesta;
import com.spring.banco.dto.RespuestaItemHistorial;
import com.spring.banco.dto.SolicitudTransaccion;
import com.spring.banco.entity.Balance;
import com.spring.banco.entity.BalanceTransactions;
import com.spring.banco.entity.Client;
import com.spring.banco.repository.BalanceRepository;
import com.spring.banco.repository.BalanceTransactionsRepository;
import com.spring.banco.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final ClientRepository clientRepository;
    private final BalanceTransactionsRepository balanceTransactionsRepository;
    private final BalanceRepository balanceRepository;

    public ResponseEntity<Respuesta> acreditar(SolicitudTransaccion solicitudTransaccion ){
        Client client = clientRepository.findById(solicitudTransaccion.getClienteId()).orElse(null);

        if(client==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Respuesta.of("error","Cliente no existe"));
        }
        Balance balance = balanceRepository.findByaccount(solicitudTransaccion.getAccount());
        if(balance!=null){
            if(balance.getClient().getId()!=client.getId())
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Respuesta.of("error","No coincide cuenta con cliente"));
            }
        }else{
            if(solicitudTransaccion.getAmount().compareTo(BigDecimal.ZERO)<=0)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Respuesta.of("error","No tiene dinero"));
            }
            balance = Balance.builder()
                    .balance(BigDecimal.ZERO)
                    .client(client)
                    .creadoEn(new Date())
                    .account(solicitudTransaccion.getAccount()).build();

            balance = balanceRepository.save(balance);
        }
        BigDecimal monto = balance.getBalance();
        monto = monto.add(solicitudTransaccion.getAmount());
        balance.setBalance(monto);
        balance = balanceRepository.save(balance);
        BalanceTransactions balanceTransactions = BalanceTransactions.builder()
                .client(client)
                .amount(solicitudTransaccion.getAmount())
                .account(balance.getId())
                .creadoEn(new Date())
                .build();
        balanceTransactionsRepository.save(balanceTransactions);
        return ResponseEntity.ok(Respuesta.of("success",balance));
    }

    public ResponseEntity<Respuesta> obtenerHistorial(Integer idCliente,String account){
        Client client = clientRepository.findById(idCliente).orElse(null);
        if(client==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Respuesta.of("error","Cliente no existe"));
        }
        List<BalanceTransactions> all = balanceTransactionsRepository.findByClient(client);

        Map<String, List<RespuestaItemHistorial>> collect = all.stream()
                // .filter(x -> x.getClient().getId().equals(idCliente))
                .map(r -> new RespuestaItemHistorial(r.getCreadoEn(), balanceRepository.findById(r.getAccount()).get().getAccount(), r.getAmount()))
                .collect(Collectors.groupingBy(RespuestaItemHistorial::getAccount));

        Map<String,Object> salida = new TreeMap<>();
        salida.put("Cliente",client);
        List<Balance> allAcount = balanceRepository.findByClient(client);

        if(account!=null){
            allAcount=allAcount.stream().filter(c->c.getAccount().equalsIgnoreCase(account)).collect(Collectors.toList());
            if(!allAcount.isEmpty())
            {
                List<RespuestaItemHistorial> respuestaItemHistorials = collect.get(account);
                collect.clear();
                collect.put(account,respuestaItemHistorials);
            }
        }
        if(!allAcount.isEmpty())
            salida.put("Cuentas",allAcount);

        if(!collect.isEmpty()){
            //return ResponseEntity.ok(Respuesta.of("error","No hay datos de transacciones"));
            salida.put("Transacciones",collect);
        }

        return ResponseEntity.ok(Respuesta.of("success",salida));
    }



}
