package com.spring.banco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SolicitudTransaccion {
    @NotNull
    Integer clienteId;

    @NotEmpty
    String account;

    @NotNull
    BigDecimal amount;
}
