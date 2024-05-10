package com.sajdakk.flipbook.utils;

import com.sajdakk.flipbook.entities.UserEntity;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private final String secretKey = "mysecretkey";

    private final JwtParser jwtParser;

    public JwtUtil() {
        this.jwtParser = Jwts.parser().setSigningKey(secretKey);
    }

    public String createToken(UserEntity user) {
        Claims claims = Jwts.claims().setSubject(user.getId().toString());
        claims.put("user_id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("name", user.getName());
        claims.put("surname", user.getSurname());
        claims.put("role", user.getRole().getId());

        return extendToken(claims);
    }

    public String extendToken(Claims claims) {
        Date tokenCreateTime = new Date();
        short accessTokenValidityMinutes = 15;
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidityMinutes));

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return jwtParser.parseClaimsJws(token).getBody();
            }

            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token expired");
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
    }

    private String resolveToken(HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("X-Auth-Token")) {
                return cookie.getValue();
            }
        }

        return null;
    }
}