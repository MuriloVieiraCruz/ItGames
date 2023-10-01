package com.muriloCruz.ItGames.entity;

import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(name = "Genero")
@Table(name = "genero")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Integer id;

    @Size(max = 100, min = 3, message = "O nome deve conter entre 3 e 100 caracteres")
    @NotBlank(message = "O nome não pode ser nulo")
    @Column(name = "nome")
    private String nome;
    
    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "O status da empresa é obrigatório")
    @Column(name = "status")
    private Status status;
}
