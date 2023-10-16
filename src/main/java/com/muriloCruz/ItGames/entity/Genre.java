package com.muriloCruz.ItGames.entity;

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
@Entity(name = "Genre")
@Table(name = "genres")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Integer id;

    @Size(max = 100, min = 3, message = "The name must contain between 3 and 100 characters")
    @NotNull(message = "The name is required")
    @Column(name = "name")
    private String name;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "The status is required")
    @Column(name = "status")
    private Status status;
    
    public Genre() {
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
