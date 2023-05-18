package com.bilel.SpringBoot_TP01.security;


import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public AuthenticationManager authManager(
            HttpSecurity http,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            UserDetailsService userDetailsService
    ) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
                .and()
                .cors()
                .configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(@NonNull HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setExposedHeaders(List.of("Authorization"));
                        configuration.setMaxAge(3600L);
                        return configuration;
                    }
                })
                .and()
                .authorizeHttpRequests()

                .requestMatchers("/login").permitAll()
                .requestMatchers("/api/teachers/all/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/api/courses/all/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/api/specialities/all/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/api/users/all/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/api/roles/all/**").hasAnyAuthority("ADMIN", "USER")


                .requestMatchers(GET, "/api/teachers/get/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(GET, "/api/courses/get/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(GET, "/api/specialities/get/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(GET, "/api/users/get/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(GET, "/api/roles/get/**").hasAnyAuthority("ADMIN", "USER")


                .requestMatchers(POST, "/api/teachers/create/**").hasAuthority("ADMIN")
                .requestMatchers(POST, "/api/courses/create/**").hasAuthority("ADMIN")
                .requestMatchers(POST, "/api/specialities/create/**").hasAuthority("ADMIN")
                .requestMatchers(POST, "/api/users/create/**").hasAuthority("ADMIN")
                .requestMatchers(POST, "/api/roles/create/**").hasAuthority("ADMIN")

                .requestMatchers(POST, "/api/users/grant/**").hasAuthority("ADMIN")
                .requestMatchers(POST, "/api/users/revoke/**").hasAuthority("ADMIN")


                .requestMatchers(PATCH, "/api/teachers/update/**").hasAuthority("ADMIN")
                .requestMatchers(PATCH, "/api/courses/update/**").hasAuthority("ADMIN")
                .requestMatchers(PATCH, "/api/specialities/update/**").hasAuthority("ADMIN")
                .requestMatchers(PATCH, "/api/users/update/**").hasAuthority("ADMIN")
                .requestMatchers(PATCH, "/api/roles/update/**").hasAuthority("ADMIN")


                .requestMatchers(DELETE, "/api/teachers/delete/**").hasAuthority("ADMIN")
                .requestMatchers(DELETE, "/api/courses/delete/**").hasAuthority("ADMIN")
                .requestMatchers(DELETE, "/api/specialities/delete/**").hasAuthority("ADMIN")
                .requestMatchers(DELETE, "/api/users/delete/**").hasAuthority("ADMIN")
                .requestMatchers(DELETE, "/api/roles/delete/**").hasAuthority("ADMIN")


                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(
                        new JWTAuthenticationFilter(authenticationManager),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(
                        new JWTAuthorizationFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }
}

