package com.muriloCruz.ItGames.entity;

import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(name = "Usuario")
@Table(name = "usuario")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  
    private Integer id;

    @Size(max = 250, min = 3, message = "O nome deve conter entre 3 e 250 caracteres")
    @NotBlank(message = "O nome não pode ser nulo")
    @EqualsAndHashCode.Include
    @Column(name = "login")
    private String login;

    @NotBlank(message = "A senha não pode ser nula")
    @Column(name = "senha")
    private String senha;

    @Size(max = 200, min = 3, message = "O nome deve conter entre 3 e 200 caracteres")
    @Email(message = "O e-mail está com o formato inválido")
    @NotBlank(message = "O e-mail não pode ser nula")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "O cpf é obrigatório")
    @CPF(message = "O cpf está incorreto")
    @Column(name = "cpf")
    private String cpf;
    
    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "O status do usuário é obrigatório")
    @Column(name = "status")
    private Status status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    @NotNull(message = "A data de nascimanto não pode ser nulo")
    @Column(name = "data_nasc")
    private Instant dataNasc;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    @NotNull(message = "A data de registro não pode ser nulo")
    @Column(name = "data_registro")
    private Instant dataRegistro;

    @DecimalMin(value = "0.0", inclusive = true, message = "O preco deve ser posistivo")
    @Digits(integer = 2, fraction = 2, message = "A avaliação deve possuir o formato 'NN.NN'")
    @Column(name = "avaliacao")
    private BigDecimal avaliacao;
    
    public Usuario() {
    	this.status = Status.A; 
    	this.avaliacao = null;
    	this.dataRegistro = Instant.now();
    }
    
    @JsonIgnore
    @Transient
    public boolean isPersistido() {
    	return getId() != null && getId() > 0;
    }
    
    @JsonIgnore
    @Transient
    public boolean isAtivo() {
    	return getStatus() == Status.A;
    }
}
