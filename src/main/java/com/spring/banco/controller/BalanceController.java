package com.spring.banco.controller;

import com.spring.banco.dto.CrearCliente;
import com.spring.banco.dto.Respuesta;
import com.spring.banco.dto.SolicitudTransaccion;
import com.spring.banco.entity.Client;
import com.spring.banco.service.BalanceService;
import com.spring.banco.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/banco/api/v1")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService service;
    private final ClientService clientService;

    @PostMapping("/client")
    public ResponseEntity<Respuesta> crearClient(@RequestBody CrearCliente cliente )
    {
        Client client = Client.builder()
                .name(cliente.getName())
                .lastName(cliente.getLastName())
                .birthday(cliente.getBirthday())
                .phone(cliente.getPhone())
                .email(cliente.getEmail())
                .address(cliente.getAddress())
                .build();

        return  clientService.crearCliente(client);
    }


    @PostMapping("/acreditar")
    public ResponseEntity<Respuesta> transaccion(@Valid @RequestBody SolicitudTransaccion solicitudTransaccion, BindingResult result){
        if(result.hasErrors()){
            String errores = this.formatMessage(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Respuesta.of("error",errores));
        }
        return service.acreditar(solicitudTransaccion);
    }

    @GetMapping("/historial/{idCliente}")
    public ResponseEntity<Respuesta> obtenerHistorial(@PathVariable(value = "idCliente",required = true) Integer idCliente,
                                                      @RequestParam(value = "account", required = false) String account)
    {
        return service.obtenerHistorial(idCliente,account);
    }

    String formatMessage(BindingResult result){

        String collect = result.getFieldErrors().stream().map(err -> {
            return err.getField() + "->" + err.getDefaultMessage();
        }).collect(Collectors.joining(","));
        return collect;
    }
}
