package com.wanted.common.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.common.redis.repository.RedisRepository;
import com.wanted.common.security.handler.AuthenticationEntryPointHandler;
import com.wanted.common.security.handler.AuthenticationFailureCustomHandler;
import com.wanted.common.security.handler.LogoutSuccessCustomHandler;
import com.wanted.common.security.service.AuthService;
import com.wanted.common.security.service.UserDetailsServiceImpl;
import com.wanted.common.security.utils.JwtProperties;
import com.wanted.common.security.utils.JwtProvider;
import com.wanted.user.mock.UserMock;
import com.wanted.user.repository.UserRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthTestConfig {
    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider(jwtProperties());
    }

    @Bean
    public JwtProperties jwtProperties() {
        return new JwtProperties();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public AuthenticationFailureCustomHandler authenticationFailureCustomHandler() {
        return new AuthenticationFailureCustomHandler(objectMapper());
    }

    @Bean
    public AuthenticationEntryPointHandler authenticationEntryPointHandler() {
        return new AuthenticationEntryPointHandler(objectMapper());
    }

    @Bean
    public LogoutSuccessCustomHandler logoutSuccessCustomHandler() {
        return new LogoutSuccessCustomHandler(redisRepository());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userRepository());
    }

    @Bean
    public AuthService authService() {
        return new AuthService(redisRepository(),jwtProvider(),userRepository(),objectMapper());
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public UserMock userMock() {
        return new UserMock(passwordEncoder());
    }

    @Bean
    public RedisRepository redisRepository() {
        return Mockito.mock(RedisRepository.class);
    }
}