package com.muriloCruz.ItGames.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(name = "Genero")
@Table(name = "genero")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Genero {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Size(max = 100, min = 3, message = "O nome deve conter entre 3 e 100 caracteres")
    @NotBlank(message = "O nome não pode ser nulo")
    @Column(name = "nome")
    private String nome;
}
