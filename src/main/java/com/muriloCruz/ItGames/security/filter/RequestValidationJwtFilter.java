package com.muriloCruz.ItGames.security.filter;

import com.muriloCruz.ItGames.security.JWTTokenManager;
import com.muriloCruz.ItGames.service.AccessCredentialsService;
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
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class RequestValidationJwtFilter extends OncePerRequestFilter{

	@Autowired
	private JWTTokenManager JWTTokenManager;

	@Autowired
	private AccessCredentialsService service;

	public static final String AUTHENTICATION_SCHEME_BEARER= "Bearer ";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader(AUTHORIZATION);
		String token = null;
		String login = null;

		boolean isHeaderAuthorizationPresent = authHeader != null
				&& StringUtils.startsWithIgnoreCase(authHeader, AUTHENTICATION_SCHEME_BEARER);

		if (isHeaderAuthorizationPresent) {
			token = authHeader.substring(7);
			login = JWTTokenManager.extractLoginFrom(token);
		}

		boolean isNewLogin = login != null && SecurityContextHolder
				.getContext().getAuthentication() == null;

		if (isNewLogin) {
			UserDetails credential = service.loadUserByUsername(login);
			if (JWTTokenManager.isValid(token, credential)) {
				UsernamePasswordAuthenticationToken authenticatedToken =
						new UsernamePasswordAuthenticationToken(credential, null, credential.getAuthorities());
				authenticatedToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticatedToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
