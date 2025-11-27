package com.auth.api.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(c -> c
                        .requestMatchers(HttpMethod.GET,"/api/books/**").hasAnyRole("USER","AUTHOR","ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/books/**").hasAnyRole("AUTHOR","ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/books/**").hasAnyRole("AUTHOR","ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/books/**").hasAnyRole("AUTHOR","ADMIN")
                        .requestMatchers("/api/comments/**").hasAnyRole("USER","AUTHOR","ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
                .build();
    }

}
