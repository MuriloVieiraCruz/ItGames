package com.muriloCruz.ItGames.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muriloCruz.ItGames.entity.enums.Disponibilidade;
import com.muriloCruz.ItGames.entity.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Entity(name = "Servico")
@Table(name = "servico")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Servico {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @NotNull(message = "A descrição não pode ser nula")
    @Column(name = "descricao")
    private String descricao;

    @DecimalMin(value = "0.0", inclusive = true, message = "O preco deve ser posistivo")
    @Digits(integer = 10, fraction = 2, message = "O preco deve possuir o formato 'NNNNNNNNNN.NN'")
    @NotNull(message = "O preco não pode ser nulo")
    @Column(name = "preco")
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "A disponibilidade não pode ser nula")
    @Column(name = "disponibilidade")
    private Disponibilidade disponibilidade;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status não pode ser nulo")
    @Column(name = "status")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "O jogo do serviço é obrigatório")
    @JoinColumn(name = "jogo_id")
    private Jogo jogo;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "O usuario do serviço é obrigatório")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postagem_id")
    private Postagem postagem;
    
    public Servico() {
    	this.status = Status.A; 
    	this.disponibilidade = Disponibilidade.ABERTO;
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
