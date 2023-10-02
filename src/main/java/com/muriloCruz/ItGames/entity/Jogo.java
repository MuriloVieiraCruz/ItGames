package com.muriloCruz.ItGames.entity;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
    @Column(name = "descricao")
    private String descricao;

    @NotNull(message = "A data de lançamento não pode ser nulo")
    @Column(name = "data_lanc")
    private Timestamp dataLanc;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "O status do jogo é obrigatório")
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "jogo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GeneroDoJogo> generos;

    @NotBlank(message = "A url da imagem é obrigatória")
    @Column(name = "imagem_url")
    private String imagemUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @Size(max = 150, min = 3, message = "A empresa deve conter entre 3 e 150 caracteres")
    @NotNull(message = "A empresa não pode ser nulo")
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
    
    public Jogo() {
    	this.status = Status.A; 
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
