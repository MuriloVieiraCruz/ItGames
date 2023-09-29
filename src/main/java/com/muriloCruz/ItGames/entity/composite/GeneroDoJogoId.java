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

	@Column(name = "id_genero")
	private Integer idDoGenero;
	
	@Column(name = "id_jogo")
	private Integer idDoJogo;
}
