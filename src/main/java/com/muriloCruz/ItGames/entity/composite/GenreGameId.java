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
public class GenreGameId {

	@Column(name = "genre_id")
	private Integer genreId;
	
	@Column(name = "game_id")
	private Integer gameId;
}
