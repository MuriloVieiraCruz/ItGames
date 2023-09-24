package com.muriloCruz.ItGames.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(name = "Servico")
@Table(name = "servico")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Postagem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Size(max = 450, message = "A url da imagem deve conterno máximo 100 caracteres")
    @Column(name = "imagem_url")
    private String imagemUrl;

    @NotNull(message = "A data de postagem não pode ser nulo")
    @Column(name = "data_post")
    private Date dataPostagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "O servico da postagem é obrigatório")
    @JoinColumn(name = "id_servico")
    private Servico servico;


}
