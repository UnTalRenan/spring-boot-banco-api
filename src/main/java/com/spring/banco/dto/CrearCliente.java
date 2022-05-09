package com.spring.banco.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
public class CrearCliente {
    String name;
    String lastName;
    Date birthday=new Date();
    String phone;
    String email;
    String address;
}
