package com.wanted.user.config;

import com.wanted.user.mapper.UserMapper;
import com.wanted.user.mock.UserMock;
import com.wanted.user.repository.UserRepository;
import com.wanted.user.service.UserService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class UserTestConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserMock userMock() {
        return new UserMock(passwordEncoder());
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository(), userMapper(), passwordEncoder());
    }
}
