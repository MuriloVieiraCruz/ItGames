package com.muriloCruz.ItGames.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.muriloCruz.ItGames.entity.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  
    private Long id;

    @Size(max = 250, min = 3, message = "The name must contain between 3 and 250 characters")
    @Email(message = "The e-mail is in an invalid format")
    @NotBlank(message = "Name cannot be null")
    @EqualsAndHashCode.Include
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Password cannot be null")
    @Column(name = "user_psswrd")
    private String password;

    @Size(max = 150, min = 3, message = "The name must contain between 3 and 150 characters")
    @NotBlank(message = "Name cannot be null")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "The cpf is mandatory")
    @CPF(message = "The cpf is incorrect")
    @Column(name = "cpf")
    private String cpf;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "The status is required")
    @Column(name = "status")
    private Status status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    @NotNull(message = "The date of birth cannot be null")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    @NotNull(message = "The registration date cannot be null")
    @Column(name = "registration_date")
    @CreationTimestamp
    private LocalDate registrationDate;

    @DecimalMin(value = "0.0", inclusive = true, message = "The price must be positive")
    @Digits(integer = 2, fraction = 2, message = "The valuation must have the format 'NN.NN'")
    @Column(name = "rating")
    private BigDecimal rating;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "The role is required")
    @Column(name = "roles")
    private Role role;

    public User() {
        this.posts = new ArrayList<>();
        this.status = Status.A;
        this.rating = null;
        this.registrationDate = LocalDate.now();
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
