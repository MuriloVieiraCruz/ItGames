package com.muriloCruz.ItGames.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity(name = "Usuario")
@Table(name = "usuario")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Size(max = 250, min = 3, message = "O nome deve conter entre 3 e 250 caracteres")
    @NotBlank(message = "O nome não pode ser nulo")
    @Column(name = "nome")
    private String nome;

    @NotBlank(message = "A senha não pode ser nula")
    @Column(name = "senha")
    private String senha;

    @Size(max = 200, min = 3, message = "O nome deve conter entre 3 e 200 caracteres")
    @NotBlank(message = "O e-mail não pode ser nulo")
    @Column(name = "email")
    private String email;

    @CPF(message = "O cpf está incorreto")
    @Column(name = "cpf")
    private String cpf;

    @NotNull(message = "A data de nascimanto não pode ser nulo")
    @Column(name = "data_nasc")
    private Date dataNasc;

    @NotNull(message = "A data de registro não pode ser nulo")
    @Column(name = "data_registro")
    private Date dataRegistro;

    @Column(name = "avaliacao")
    private Double avaliacao;
}
