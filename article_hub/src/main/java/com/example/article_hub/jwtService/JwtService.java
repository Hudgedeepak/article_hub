package com.example.article_hub.jwtService;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

   
	public static final String SECRET = "LNe+qW+mRC8KoH8qgD6FQxch+lpAxtnPYT8XbOwXLrI=";

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}
	
	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*120))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}
	
	public Boolean validateToken( String token, UserDetails userDetails ) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date()) ;
	}
	
	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}
	
	
	public Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
		
	} 
	
	public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);}
	
  
    private SecretKey getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	private Claims extractAllClaims(String token) {
	return Jwts 	
			.parserBuilder()
			.setSigningKey(getSignKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	
	
	
	
}
