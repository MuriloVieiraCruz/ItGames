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
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Entity(name = "Post")
@Table(name = "post")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "The description is required")
    @Column(name = "description")
    private String description;

    @DecimalMin(value = "0.0", inclusive = true, message = "The price must be positive")
    @Digits(integer = 4, fraction = 2, message = "The price must have the format 'NNNN.NN'")
    @NotNull(message = "The price is required")
    @Column(name = "price")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The availability is required")
    @Column(name = "availability")
    private Availability  availability;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The status is required")
    @Column(name = "status")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The service game is required")
    @JoinColumn(name = "game_id")
    private Game game;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The user of the service is required")
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The user of the service is required")
    @JoinColumn(name = "freelancer_id")
    private User freelancer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
    @NotNull(message = "The post date is required")
    @Column(name = "post_date")
    private LocalDate postDate;

//    @NotBlank(message = "The image url is required")
//    @Column(name = "image_url")
//    @Lob
//    private String imageUrl;
    
    public Post() {
    	this.status = Status.A; 
    	this.availability = Availability.OPEN;
        this.postDate = LocalDate.now();
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
