package com.muriloCruz.ItGames.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity(name = "Enterprise")
@Table(name = "enterprise")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Enterprise {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Long id;
    
    @Size(max = 100, min = 3, message = "The name must contain between 3 and 200 characters")
    @NotNull(message = "The name is required")
    @EqualsAndHashCode.Include
    @Column(name = "name")
    private String name;
    
    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "The status is required")
    @Column(name = "status")
    private Status status;

    @Size(max = 14, min = 14, message = "The cnpj must contain 14 characters")
    @NotBlank(message = "The cnpj is required")
    @Column(name = "cnpj")
    private String cnpj;
    
    public Enterprise() {
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
