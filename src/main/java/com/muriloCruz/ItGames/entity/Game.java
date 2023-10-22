package com.muriloCruz.ItGames.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@Entity(name = "Game")
@Table(name = "games")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Game {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Size(max = 100, min = 3, message = "The name must contain between 3 and 100 characters")
    @NotBlank(message = "The name cannot be null")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Name cannot be null")
    @Column(name = "description")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    @NotNull(message = "The release date cannot be null")
    @Column(name = "release_date")
    private Instant releaseDate;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Game status is required")
    @Column(name = "status")
    private Status status;

    @NotBlank(message = "Image url is required")
    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The enterprise cannot be null")
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
