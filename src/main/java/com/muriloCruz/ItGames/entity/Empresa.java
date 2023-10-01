package com.muriloCruz.ItGames.entity;

import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(name = "Empresa")
@Table(name = "empresa")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Empresa {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 200, min = 3, message = "O nome deve conter no máximo 200 car")
    @NotNull(message = "O nome é obrigatório")
    @Column(name = "nome")
    private String nome;
    
    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "O status da empresa é obrigatório")
    @Column(name = "status")
    private Status status;
}
