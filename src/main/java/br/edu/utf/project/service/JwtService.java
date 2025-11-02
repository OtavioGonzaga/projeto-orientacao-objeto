package br.edu.utf.project.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

	private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	/**
	 * Gera um JWT assinado
	 * 
	 * @param subject    normalmente o id do usuário
	 * @param expiration tempo para expiração do token em milisegundos
	 * @return String do JWT
	 */
	public String generateToken(String subject, Long expiration) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expiration);

		return Jwts.builder()
				.setSubject(subject)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(secretKey)
				.compact();
	}

	/**
	 * Extrai e retorna o subject de um JWT
	 * @param token JWT
	 * @return subject do JWT (normalmente o id do usuário)
	 */
	public String getSubject(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}
