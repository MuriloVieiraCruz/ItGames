package com.muriloCruz.ItGames.security;

import java.util.List;

import com.muriloCruz.ItGames.service.impl.AccessCredentialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfig {

	@Autowired
	private AutenticationFilterJwt autenticationFilterJwt;
	
	@Autowired
	private AccessCredentialServiceImpl service;

	private AccessDeniedHandler accessDeniedHandler;

	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
    public AuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(service);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
	
	@Bean
	public UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource() {
	    CorsConfiguration corsConfiguration = new CorsConfiguration();
	    corsConfiguration.applyPermitDefaultValues(); 
	    corsConfiguration.setAllowedHeaders(List.of("*"));
	    corsConfiguration.setAllowedMethods(List.of("*"));
	    corsConfiguration.setAllowedOrigins(List.of("*"));
	    corsConfiguration.setExposedHeaders(List.of("*"));
	    UrlBasedCorsConfigurationSource ccs = new UrlBasedCorsConfigurationSource();
	    ccs.registerCorsConfiguration("/**", corsConfiguration);
	    return ccs;
	}
	
	@Bean	
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http		
		.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests((request) -> 
				request
//					.requestMatchers("/**")
//						.permitAll()
				//.anyRequest().authenticated())
						.anyRequest().permitAll())
			.sessionManagement(manager -> 
				manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider()).addFilterBefore(
						autenticationFilterJwt, UsernamePasswordAuthenticationFilter.class)
			.cors(c -> urlBasedCorsConfigurationSource())
			.exceptionHandling((ex) -> {
				ex.accessDeniedHandler(accessDeniedHandler);
			});
	    return http.build();
	}
	
	
}
