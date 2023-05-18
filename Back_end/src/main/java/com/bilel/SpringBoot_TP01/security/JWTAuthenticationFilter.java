package com.bilel.SpringBoot_TP01.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bilel.SpringBoot_TP01.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.bilel.SpringBoot_TP01.security.SecurityParams.EXP_TIME;
import static com.bilel.SpringBoot_TP01.security.SecurityParams.SECRET_KEY;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super();
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        User user =null;
        try {
            user = new ObjectMapper().readValue(
                    request.getInputStream(),
                    User.class
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert user != null;
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
    }
    @Override
    protected void successfulAuthentication (
            HttpServletRequest request,
            HttpServletResponse response, FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        List<String> roles = new ArrayList<>();
        springUser.getAuthorities().forEach(auth-> {
            roles.add(auth.getAuthority());
        });
        String jwt = JWT.create().
                withSubject(springUser.getUsername()).
                withArrayClaim("roles", roles.toArray(new String[roles.size()])).
                withExpiresAt(new Date(
                        System.currentTimeMillis() + EXP_TIME)
                )
                .sign(Algorithm.HMAC256(SECRET_KEY));
        response.addHeader("Authorization", jwt);
    }
}
