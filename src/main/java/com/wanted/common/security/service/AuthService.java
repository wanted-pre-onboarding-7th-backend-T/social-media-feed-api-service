package com.wanted.common.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.common.exception.CommonException;
import com.wanted.common.redis.repository.RedisRepository;
import com.wanted.common.security.dto.UserInfo;
import com.wanted.common.security.enums.AuthExceptionCode;
import com.wanted.common.security.utils.JwtProvider;
import com.wanted.user.entity.User;
import com.wanted.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RedisRepository redis;
    private final JwtProvider provider;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public void reissue(HttpServletRequest request, HttpServletResponse response) {
        UserInfo info = findUserInfo(findCookie(request));
        User findUser = validRefreshTokenSubject(info);

        String refresh = provider.generateRefreshToken(findUser.getUserName());
        String access = provider.generateAccessToken(findUser.getUserName(), findUser.getId(),
                info.getAuthorities());

        saveUserInfoToRedis(refresh, info);

        response.setHeader(HttpHeaders.AUTHORIZATION, access);
        response.addCookie(createCookie(refresh));
    }

    private void saveUserInfoToRedis(String refresh, UserInfo info) {
            redis.save(refresh, transString(info), provider.getRefreshTokenValidityInSeconds());
    }

    private User validRefreshTokenSubject(UserInfo userInfo) {
        return userRepository.findByUserName(userInfo.getUserName())
                .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다"));
    }

    private Cookie findCookie(HttpServletRequest request) {
        return searchCookieProperties(validCookiesExist(request));
    }

    private UserInfo findUserInfo(Cookie refreshCookie) {
        return transUserInfo(refreshCookie);
    }

    private String findAndDeleteToRedis(Cookie refreshCookie) {
        String tokenToRedis = findTokenToRedis(refreshCookie);
        deleteToken(tokenToRedis);
        return tokenToRedis;
    }

    private void deleteToken(String tokenToRedis) {
        redis.delete(tokenToRedis);
    }

    private String findTokenToRedis(Cookie refreshCookie) {
        return Optional.ofNullable(redis.findByKey(refreshCookie.getValue()))
                .orElseThrow(() -> new CommonException(
                        AuthExceptionCode.REFRESH_TOKEN_NOT_FOUND.getHttpStatus(),
                        AuthExceptionCode.REFRESH_TOKEN_NOT_FOUND.getMessage()));
    }

    private Cookie[] validCookiesExist(HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies()).orElse(new Cookie[]{});
    }

    private Cookie searchCookieProperties(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("Refresh"))
                .findFirst()
                .orElseThrow(() -> new CommonException(
                        AuthExceptionCode.REFRESH_TOKEN_EXPIRED.getHttpStatus(),
                        AuthExceptionCode.REFRESH_TOKEN_EXPIRED.getMessage()));
    }

    private Cookie createCookie(String value) {
        Cookie cookie = new Cookie("Refresh", value);
        cookie.setDomain("localhost");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(provider.getRefreshTokenValidityInSeconds() * 60);
        cookie.setPath("/api/auth/reissue");
        cookie.setSecure(true);
        return cookie;
    }

    private UserInfo transUserInfo(Cookie refreshCookie) {
        try {
            return objectMapper.readValue(findAndDeleteToRedis(refreshCookie), UserInfo.class);
        } catch (JsonProcessingException e) {
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR,"JSON 변환 에러");
        }
    }

    private String transString(UserInfo info) {
        try {
            return objectMapper.writeValueAsString(info);
        } catch (JsonProcessingException e) {
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR,"JSON 변환 에러");
        }
    }
}
