package com.nicat.socailmedia.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private final Clock clock = DefaultClock.INSTANCE;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    private <T> T getClaimForToken(String token, Function<Claims, T> claimsResolver){
        Claims claims = getAllClaimsForToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsForToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject){
        Date createdDate = clock.now();
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        String email = getUsernameFromToken(token);

        return Objects.equals(email, userDetails.getUsername()) &&
                isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        Date expiration = getExpirationDateFromToken(token);
        return !expiration.before(clock.now());
    }

    private Date getExpirationDateFromToken(String token){
        return getClaimForToken(token, Claims::getExpiration);
    }

    public String getUsernameFromToken(String token){
        return getClaimForToken(token, Claims::getSubject);
    }
}
