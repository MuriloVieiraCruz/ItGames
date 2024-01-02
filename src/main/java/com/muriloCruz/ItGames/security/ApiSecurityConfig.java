package com.muriloCruz.ItGames.security;

import com.muriloCruz.ItGames.security.filter.CsrfCookieFilter;
import com.muriloCruz.ItGames.security.filter.RequestValidationJwtFilter;
import com.muriloCruz.ItGames.service.AccessCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class ApiSecurityConfig {

    @Autowired
    private RequestValidationJwtFilter requestValidationJwtFilter;

    @Autowired
    private AccessCredentialsService service;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(service);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

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

        http
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .cors((cors) -> corsFilter())
            .csrf(c -> {
                c.csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers("/login/register", "/login/authentication", "/contact")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
            })
            .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
            .addFilterBefore(requestValidationJwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(request ->
                    request
                            .requestMatchers("/login/register", "/login/authentication")
                            .permitAll()
                            .requestMatchers(HttpMethod.PATCH, "/post/freelancer")
                            .hasAuthority("USER")
                            .requestMatchers(HttpMethod.POST, "/post")
                            .hasAnyAuthority("USER", "ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/post")
                            .hasAnyAuthority("USER", "ADMIN")
                            .requestMatchers(HttpMethod.PATCH, "/post")
                            .hasAnyAuthority("USER", "ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/post")
                            .hasAnyAuthority("USER", "ADMIN")
                            .requestMatchers(HttpMethod.POST, "/enterprise", "/game", "/genre", "/genre_game")
                            .hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/enterprise", "/game", "/genre", "/genre_game")
                            .hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.PATCH, "/enterprise", "/game", "/genre", "/genre_game")
                            .hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/enterprise", "/game", "/genre", "/genre_game")
                            .hasAuthority("ADMIN")
                            .anyRequest().authenticated());
        return http.build();
    }
}
