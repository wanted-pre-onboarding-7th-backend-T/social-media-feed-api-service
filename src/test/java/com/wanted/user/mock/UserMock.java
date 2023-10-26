package com.wanted.user.mock;

import com.wanted.user.dto.request.UserPostRequestDto;
import com.wanted.user.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMock {

    private final PasswordEncoder encoder;
    private final Long userId = 1L;
    private final String email = "test@gmail.com";
    private final String username = "테스트김";
    private final String rawPassword = "test131!$@2";

    public UserMock(PasswordEncoder passwordEncoder) {
        this.encoder = passwordEncoder;
    }

    public UserPostRequestDto postRequestMock() {
        return UserPostRequestDto.builder()
                .userName(username)
                .email(email)
                .password(rawPassword)
                .build();
    }

    public User entityMock() {
        return User.builder()
                .id(userId)
                .password(encoder.encode(rawPassword))
                .email(email)
                .userName(username)
                .build();
    }

    public Long getUserId() {
        return userId;
    }
}

