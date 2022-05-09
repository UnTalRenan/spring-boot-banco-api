package com.spring.banco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class RespuestaItemHistorial {
    Date creadoEn;
    String account;
    BigDecimal amount;
}
