package com.muriloCruz.ItGames.security.config;

import com.muriloCruz.ItGames.security.filter.CsrfCookieFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class ApiSecurityConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedOriginPatterns(List.of("*"));
        corsConfiguration.setExposedHeaders(List.of("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloackRoleConverter());

        http
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(cors -> corsFilter())
            .csrf(c -> {
                c.csrfTokenRequestHandler(requestHandler).csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
            })
            .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
            .authorizeHttpRequests(request ->
                    request
                            .requestMatchers(HttpMethod.PATCH, "/post/freelancer")
                            .hasRole("USER")
                            .requestMatchers(HttpMethod.POST, "/post")
                            .hasAnyRole("USER", "ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/post")
                            .hasAnyRole("USER", "ADMIN")
                            .requestMatchers(HttpMethod.PATCH, "/post")
                            .hasAnyRole("USER", "ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/post")
                            .hasAnyRole("USER", "ADMIN")
                            .requestMatchers(HttpMethod.POST, "/enterprise", "/game", "/genre", "/genre_game")
                            .hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/enterprise", "/game", "/genre", "/genre_game")
                            .hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PATCH, "/enterprise", "/game", "/genre", "/genre_game")
                            .hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/enterprise", "/game", "/genre", "/genre_game")
                            .hasRole("ADMIN")
                            .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2ResourceServerCustomizer ->
                        oauth2ResourceServerCustomizer.jwt(jwtCustomizer -> jwtCustomizer.jwtAuthenticationConverter(jwtAuthenticationConverter)));
        return http.build();
    }
}
