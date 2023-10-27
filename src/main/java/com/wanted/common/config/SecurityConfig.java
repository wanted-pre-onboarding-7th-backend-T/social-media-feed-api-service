package com.wanted.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.common.redis.repository.RedisRepository;
import com.wanted.common.security.filter.JwtAuthenticationFilter;
import com.wanted.common.security.filter.JwtVerificationFilter;
import com.wanted.common.security.handler.AuthenticationEntryPointHandler;
import com.wanted.common.security.handler.AuthenticationFailureCustomHandler;
import com.wanted.common.security.handler.LogoutSuccessCustomHandler;
import com.wanted.common.security.utils.JwtProperties;
import com.wanted.common.security.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationEntryPointHandler authenticationEntryPointHandler;
    private final LogoutSuccessCustomHandler logoutSuccessCustomHandler;
    private final AuthenticationFailureCustomHandler authenticationFailureCustomHandler;
    private final JwtProvider provider;
    private final JwtProperties properties;
    private final ObjectMapper objectMapper;
    private final RedisRepository repository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.apply(new CustomFilterConfigurer());
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/reissue").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(
                        authenticationEntryPointHandler))
                .logout(logout -> logout.logoutSuccessHandler(logoutSuccessCustomHandler)
                        .logoutUrl("/api/logout"));
        return http.build();
    }

    public class CustomFilterConfigurer extends
            AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {

        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(
                    AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(provider,
                    objectMapper, repository, properties);
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");
            jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
            jwtAuthenticationFilter.setAuthenticationFailureHandler(
                    authenticationFailureCustomHandler);

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(provider);

            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterBefore(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }
}