package com.muriloCruz.ItGames.entity;

import java.util.Date;
import java.util.List;

import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "O status do jogo é obrigatório")
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "jogo", fetch = FetchType.LAZY)
    private List<GeneroDoJogo> generos;

    @NotBlank(message = "A url da imagem é obrigatória")
    @Column(name = "imagem_url")
    private String imagemUrl;

    @Size(max = 150, min = 3, message = "A empresa deve conter entre 3 e 150 caracteres")
    @NotNull(message = "A empresa não pode ser nulo")
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
}
