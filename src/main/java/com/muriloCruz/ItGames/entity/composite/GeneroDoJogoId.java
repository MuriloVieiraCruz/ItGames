package com.muriloCruz.ItGames.entity.composite;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class GeneroDoJogoId {

	@Column(name = "genero_id")
	private Integer idDoGenero;
	
	@Column(name = "jogo_id")
	private Integer idDoJogo;
}
