package com.teluguskillhub.taskproject.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.teluguskillhub.taskproject.exception.APIException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtParser;

@Component
public class JwtTokenProvider {
	private SecretKey secretKey = Keys.hmacShaKeyFor("JWTSecretKeysklklshskekkskjkejkskjk".getBytes());

	public String generateToken(Authentication authentication) {
		String email = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + 3600000);// milliseconds
		String token = Jwts.builder().setSubject(email).setIssuedAt(currentDate).setExpiration(expireDate)
				.signWith(secretKey, Jwts.SIG.HS256) // Use the SecretKey for signing
				.compact();
		return token;
	}

	public String getEmailFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();

//		JwtParser parser = Jwts.parser().setSigningKey(secretKey).build();
////                .setSigningKey(secretKey) // Use the same secret key to verify the signature
////                .build();
//		 Claims claims = parser.parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public boolean validToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
			return true;
		} catch (Exception e) {
			throw new APIException("Token issue : " + e.getMessage());
		}

	}
}
