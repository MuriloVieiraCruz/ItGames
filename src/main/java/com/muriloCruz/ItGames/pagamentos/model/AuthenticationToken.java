//package com.muriloCruz.ItGames.pagamentos.model;
//
//import java.util.Date;
//import java.util.UUID;
//
//import com.muriloCruz.ItGames.entity.Usuario;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToOne;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@Entity
//public class AuthenticationToken {
//
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Integer id;
//
//
//    private String token;
//
//    @Column(name = "created_date")
//    private Date createdDate;
//
//    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false, name = "user_id")
//    private Usuario user;
//
//    public AuthenticationToken(Usuario user) {
//        this.user = user;
//        this.createdDate = new Date();
//        this.token = UUID.randomUUID().toString();
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String Token) {
//        this.token = Token;
//    }
//
//    public Date getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(Date createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public Usuario getUser() {
//        return user;
//    }
//
//    public void setUser(Usuario user) {
//        this.user = user;
//    }
//}
