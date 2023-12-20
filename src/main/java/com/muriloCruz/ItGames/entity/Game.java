package com.muriloCruz.ItGames.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@Entity(name = "Game")
@Table(name = "game")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Game {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Size(max = 100, min = 3, message = "The name must contain between 3 and 100 characters")
    @NotBlank(message = "The name is required")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "The description is required")
    @Column(name = "description")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
    @NotNull(message = "The release date is required")
    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "The game status is required")
    @Column(name = "status")
    private Status status;

//    @Column(name = "image_url")
//    @Lob
//    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The enterprise is required")
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GenreGame> genres;

    public Game() {
        this.genres = new ArrayList<>();
    	this.status = Status.A; 
    }
    
    @JsonIgnore
    @Transient
    public boolean isPersisted() {
    	return getId() != null && getId() > 0;
    }
    
    @JsonIgnore
    @Transient
    public boolean isActive() {
    	return getStatus() == Status.A;
    }
}
