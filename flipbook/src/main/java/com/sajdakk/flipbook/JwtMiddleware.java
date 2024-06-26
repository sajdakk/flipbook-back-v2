package com.sajdakk.flipbook;

import com.sajdakk.flipbook.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtMiddleware extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtMiddleware(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            final Claims claims = jwtUtil.resolveClaims(request);
            if (claims != null) {
                // Retrieve existing X-Auth-Token cookie
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("X-Auth-Token")) {
                            cookie.setValue(jwtUtil.extendToken(claims));
                            cookie.setPath("/");

                            response.addCookie(cookie);
                            break;
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }

        filterChain.doFilter(request, response);
    }

}
