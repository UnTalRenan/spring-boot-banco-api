package com.spring.banco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class SolicitudHistorial {

    String cuenta;

    @NotNull
    Integer idCliente;
}
