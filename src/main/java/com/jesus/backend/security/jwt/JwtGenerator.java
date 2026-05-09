package com.jesus.backend.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtGenerator {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    // Clave criptográfica válida para trabajar con JWT
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // Generar el token
    public String generateToken(Authentication authentication) {
        // Obtenemos el usuario
        String email = authentication.getName();

        // Fecha de expedición del token
        Date currentDate = new Date();

        // Fecha de expiración del token
        Date expireDate = new Date(currentDate.getTime() + jwtExpiration);

        // Construimos el token
        String token = Jwts.builder()
                .subject(email)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .claim(
                        "roles",
                        authentication.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList()
                )
                .compact();

        return token;
    }

    // Método actualizado para obtener el nombre de usuario del token
    public String getUsernameFromJwt(String token) {
        // La forma moderna: parser().verifyWith(...).parseSignedClaims(...).getPayload()
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey()) // Asegurarse de que el casting es correcto
                .build()
                .parseSignedClaims(token) // parseSignedClaims en lugar de parseClaimsJws
                .getPayload(); // getPayload en lugar de getBody

        return claims.getSubject();
    }

    // Método actualizado para validar el token
    public boolean validateToken(String token) {
        try {
            // La forma moderna para validación también
            Jwts.parser()
                    .verifyWith((SecretKey) getSigningKey())
                    .build()
                    .parseSignedClaims(token); // Aquí solo nos interesa que no lance excepción

            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        } catch (SignatureException e) { // Importante para HS512
            System.out.println("Signature validation failed: " + e.getMessage());
        }
        return false;
    }


}
