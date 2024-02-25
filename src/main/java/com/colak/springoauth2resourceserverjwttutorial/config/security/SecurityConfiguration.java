package com.colak.springoauth2resourceserverjwttutorial.config.security;

import com.colak.springoauth2resourceserverjwttutorial.config.AppJwtProperties;
import com.colak.springoauth2resourceserverjwttutorial.config.security.accessdenied.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AppJwtProperties appJwtProperties;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(configurer -> configurer.jwt(Customizer.withDefaults()))
                // Configures an authentication entry point to handle unauthorized access (AccessDeniedException)
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(customAccessDeniedHandler()))
                .build();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(appJwtProperties.getKey()).build();
    }

}
