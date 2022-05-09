package com.spring.banco.service;

import com.spring.banco.dto.Respuesta;
import com.spring.banco.entity.Client;
import com.spring.banco.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public ResponseEntity<Respuesta> crearCliente(Client client)
    {
        return ResponseEntity.ok(Respuesta.of("Success",clientRepository.save(client)));
    }

}
