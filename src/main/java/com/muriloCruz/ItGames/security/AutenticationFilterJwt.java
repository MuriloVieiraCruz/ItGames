package com.muriloCruz.ItGames.security;

import com.muriloCruz.ItGames.service.impl.AccessCredentialServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AutenticationFilterJwt extends OncePerRequestFilter {

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Autowired
    private AccessCredentialServiceImpl accessCredentialService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String login = null;

        boolean isHeaderAuthorizationPresent = authHeader != null && authHeader.startsWith("Bearer ");
        if (isHeaderAuthorizationPresent) {
            token = authHeader.substring(7);
            login = jwtTokenManager.extractLoginFrom(token);
        }

        boolean isNewLogin = login != null && SecurityContextHolder
                .getContext().getAuthentication() == null;

        if (isNewLogin) {
            UserDetails credencial = accessCredentialService.loadUserByUsername(login);
            if (jwtTokenManager.isValid(token, credencial)) {
                UsernamePasswordAuthenticationToken authenticateToken =
                        new UsernamePasswordAuthenticationToken(
                                credencial, null, credencial.getAuthorities());
                authenticateToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticateToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
