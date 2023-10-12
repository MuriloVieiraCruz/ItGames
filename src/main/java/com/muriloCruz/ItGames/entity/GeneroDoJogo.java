package com.muriloCruz.ItGames.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muriloCruz.ItGames.entity.composite.GeneroDoJogoId;
import com.muriloCruz.ItGames.entity.enums.TipoAssociacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "GeneroDoJogo")
@Table(name = "genero_jogo")
public class GeneroDoJogo {

	@EmbeddedId
	@EqualsAndHashCode.Include
	@NotNull(message = "O id do genero do jogo é obrigatório")
	private GeneroDoJogoId id;
	
	@Enumerated(EnumType.STRING)
	@NotBlank(message = "O tipo da associação é obrigatório")
	@Column(name = "tipo_associacao")
	private TipoAssociacao tipoAssociacao;
	
	@ToString.Exclude
	@MapsId("idDoJogo")
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "O jogo é obrigatório")
	@JoinColumn(name = "jogo_id")
	private Jogo jogo;
	
	@ToString.Exclude
	@MapsId("idDoGenero")
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "O genero é obrigatório")
	@JoinColumn(name = "genero_id")
	private Genero genero;
	
    @JsonIgnore
    @Transient
    public boolean isPersistido() {
    	return getId() != null 
    			&& getId().getIdDoGenero() > 0
    			&& getId().getIdDoJogo() > 0;
    }	
}
