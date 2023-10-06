package com.muriloCruz.ItGames.entity;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

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
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(name = "Postagem")
@Table(name = "postagem")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Postagem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "imagem_url")
    private String imagemUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    @NotNull(message = "A data de postagem não pode ser nulo")
    @Column(name = "data_post")
    private Instant dataPostagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "O servico da postagem é obrigatório")
    @JoinColumn(name = "servico_id")
    private Servico servico;
}
