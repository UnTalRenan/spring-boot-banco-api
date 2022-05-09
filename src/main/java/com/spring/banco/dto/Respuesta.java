package com.spring.banco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Respuesta {
    String estatus;
    Object respuesta;
}
