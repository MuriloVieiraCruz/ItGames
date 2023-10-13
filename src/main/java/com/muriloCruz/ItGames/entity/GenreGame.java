package com.muriloCruz.ItGames.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muriloCruz.ItGames.entity.composite.GenreGameId;
import com.muriloCruz.ItGames.entity.enums.TypeAssociation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "GenreGame")
@Table(name = "genres_games")
public class GenreGame {

	@EmbeddedId
	@EqualsAndHashCode.Include
	@NotNull(message = "The game genre ID is required")
	private GenreGameId id;
	
	@Enumerated(EnumType.STRING)
	@NotBlank(message = "The association type is required")
	@Column(name = "association_type")
	private TypeAssociation typeAssociation;
	
	@ToString.Exclude
	@MapsId("gameId")
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "The game is required")
	@JoinColumn(name = "game_id")
	private Game game;
	
	@ToString.Exclude
	@MapsId("genreId")
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "The genre is required")
	@JoinColumn(name = "genre_id")
	private Genre genre;
	
    @JsonIgnore
    @Transient
    public boolean isPersisted() {
    	return getId() != null 
    			&& getId().getGenreId() > 0
    			&& getId().getGameId() > 0;
    }	
}
