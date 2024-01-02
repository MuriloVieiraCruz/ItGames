package com.muriloCruz.ItGames.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Component
public class JWTTokenManager {

	@Value("${spring.jwt.secret}")
	private String secret;
	
	@Value("${spring.jwt.ttl-in-millis}")
	private int ttlInMillis;
	
	private Key getSignatureKey() {
		byte[] keyByte = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyByte);
	}
	
	public String generateTokenBy(String login) {
		return Jwts.builder()
				.setClaims(new HashMap<String, Object>())
				.setSubject(login)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + ttlInMillis))
				.signWith(getSignatureKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	private Claims extractClaimsFrom(String generatedToken) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignatureKey())
				.build()
				.parseClaimsJws(generatedToken)
				.getBody();
	}
	
	public String extractLoginFrom(String generatedToken) {
		Claims details = extractClaimsFrom(generatedToken);
		return details.getSubject();
	}
	
	public Date extractValidityFrom(String generatedToken) {
		Claims details = extractClaimsFrom(generatedToken);
		return details.getExpiration();
	}
	
	public boolean isExpired(String generatedToken) {
		Date valid = extractValidityFrom(generatedToken);
		return valid.before(new Date());
	}
	
	public boolean isValid(String generatedToken , UserDetails credential) {
		String login = extractLoginFrom(generatedToken);
		boolean isLoginValid = login.equals(credential.getUsername());
		boolean isDataValid = !isExpired(generatedToken);
		return isLoginValid && isDataValid;
	}
	
}
