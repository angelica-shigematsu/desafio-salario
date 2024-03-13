package br.com.unisoma.funcionario.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="worker")
@Getter
@Setter
@NoArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue
    @Column(name="id", unique = true)
    private long id;

    @Column(name="fullname", nullable = false)
    private String fullname;

    @Column(name="cpf", nullable = false)
    private String cpf;

    @Column(name="birth_date", nullable = false)
    private Date birthDate;

    @Column(name="phone", nullable = false)
    private String phone;

    @Column(name="address", nullable = false)
    private String address;

    @Column(name="salary", nullable = false)
    private double salary;
}
