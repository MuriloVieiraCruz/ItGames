package com.muriloCruz.ItGames.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muriloCruz.ItGames.entity.enums.Availability;
import com.muriloCruz.ItGames.entity.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@Entity(name = "Service")
@Table(name = "services")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Service {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @NotNull(message = "The description cannot be null")
    @Column(name = "description")
    private String description;

    @DecimalMin(value = "0.0", inclusive = true, message = "The price must be positive")
    @Digits(integer = 10, fraction = 2, message = "The price must have the format 'NNNNNNNNNNN.NN'")
    @NotNull(message = "The price cannot be null")
    @Column(name = "price")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The availability cannot be null")
    @Column(name = "availability")
    private Availability  availability;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status cannot be null")
    @Column(name = "status")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The service game is mandatory")
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The user of the service is required")
    @JoinColumn(name = "user_id")
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    @NotNull(message = "The post date cannot be null")
    @Column(name = "post_date")
    private Instant postDate;

    @NotBlank(message = "Image url is required")
    @Column(name = "image_url")
    private String imageUrl;
    
    public Service() {
    	this.status = Status.A; 
    	this.availability = Availability.OPEN;
        this.postDate = Instant.now();
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
