package br.com.unisoma.funcionario.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FuncionarioDTO {
    private String fullname;

    private String cpf;

    private Date birthDate;

    private String phone;

    private String address;

    private double salary;
}
