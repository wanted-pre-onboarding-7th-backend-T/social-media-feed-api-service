package com.wanted.user.mock;

import com.wanted.common.dto.ResponseDto;
import com.wanted.common.security.dto.LoginDto;
import com.wanted.common.security.dto.UserInfo;
import com.wanted.user.dto.request.UserPostRequestDto;
import com.wanted.user.dto.response.UserIdResponseDto;
import com.wanted.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMock {

    private final PasswordEncoder encoder;
    private final Long userId = 1L;
    private final String email = "test@gmail.com";
    private final String username = "testKim001";
    private final String rawPassword = "test131!$@2";
    private final String wrongPassword = "test131!$@2!32!23";
    private final String samePasswordWithUsername = username;
    private final String notEnoughPasswordLength = "1q2w3e";
    private final String sameThreeWordPassword = "111Q2@w#e$r";
    private final String notTwoKindOfWordPassword = "21315235446123";

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

    public LoginDto loginMock() {
        return new LoginDto(username,rawPassword);
    }

    public LoginDto wrongLoginMock() {
        return new LoginDto(username,wrongPassword);
    }

    public UserInfo userInfoMock() {
        return new UserInfo(username,"ROLE_USER");
    }

    public ResponseDto<UserIdResponseDto> postResponseMock() {
        return ResponseDto.<UserIdResponseDto>builder()
                .code(HttpStatus.CREATED.value())
                .data(new UserIdResponseDto(userId))
                .message(HttpStatus.CREATED.getReasonPhrase())
                .build();
    }

    public UserPostRequestDto postMock(){
        return new UserPostRequestDto(username,email,rawPassword);
    }


    public UserPostRequestDto postMockPasswordSameWithUsername(){
        return new UserPostRequestDto(username,email,samePasswordWithUsername);
    }

    public UserPostRequestDto postMockPasswordNotEnoughLength(){
        return new UserPostRequestDto(username,email,notEnoughPasswordLength);
    }

    public UserPostRequestDto postMockPasswordNotTwoKindOfWordPassword() {
        return new UserPostRequestDto(username,email,notTwoKindOfWordPassword);
    }

    public UserPostRequestDto postMockPasswordSameThreeWordPassword() {
        return new UserPostRequestDto(username,email,sameThreeWordPassword);
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getRawPassword() {
        return rawPassword;
    }
}

