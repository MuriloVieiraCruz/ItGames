package com.muriloCruz.ItGames.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Entity(name = "Jogo")
@Table(name = "jogo")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Jogo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Size(max = 100, min = 3, message = "O nome deve conter entre 3 e 100 caracteres")
    @NotBlank(message = "O nome não pode ser nulo")
    @Column(name = "nome")
    private String nome;

    @NotBlank(message = "O nome não pode ser nulo")
    @Column(name = "nome")
    private String descricao;

    @NotNull(message = "A data de lançamento não pode ser nulo")
    @Column(name = "data_lanc")
    private Date dataLanc;

    @Size(max = 25, message = "O gênero deve conter no máximo 25 caracteres")
    @NotBlank(message = "O gênero não pode ser nulo")
    @Column(name = "genero")
    private String genero;

    @Size(max = 450, message = "O gênero deve conter no máximo 450 caracteres")
    @Column(name = "imagem_url")
    private String imagemUrl;

    @Size(max = 150, min = 3, message = "A empresa deve conter entre 3 e 150 caracteres")
    @NotBlank(message = "A empresa não pode ser nulo")
    @Column(name = "empresa")
    private String empresa;
}
