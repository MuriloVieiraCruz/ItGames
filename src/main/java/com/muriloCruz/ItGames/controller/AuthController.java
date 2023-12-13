package com.muriloCruz.ItGames.controller;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.entity.TokenSolicitation;
import com.muriloCruz.ItGames.security.JwtTokenManager;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> logIn(@RequestBody @Valid TokenSolicitation tokenSolicitation){
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(tokenSolicitation.getLogin(), tokenSolicitation.getPassword()));
//        Preconditions.checkArgument(AuthenticatedToken.isAuthenticated(),
//                "Login e/ou senha estão inválidos");
        String generatedToken = jwtTokenManager.generateTokenBy(tokenSolicitation.getLogin());
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("token", generatedToken);
        return ResponseEntity.ok(response);
    }

}
