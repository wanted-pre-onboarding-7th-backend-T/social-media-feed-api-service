package com.wanted.common.security.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.common.redis.repository.RedisRepository;
import com.wanted.common.security.dto.LoginDto;
import com.wanted.common.security.dto.UserInfo;
import com.wanted.common.security.utils.JwtProperties;
import com.wanted.common.security.utils.JwtProvider;
import com.wanted.common.security.vo.Principal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final RedisRepository repository;
    private final JwtProperties jwtProperties;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        var authenticationToken = createAuthenticationToken(toTransDto(request));
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws JsonProcessingException {
        Principal principal = (Principal) authResult.getPrincipal();

        String accessToken =
                jwtProvider.generateAccessToken(principal.getUsername(), principal.getId(),
                        toTrans(principal.getAuthorities()));
        String refreshToken = jwtProvider.generateRefreshToken(principal.getUsername());

        repository.save(refreshToken, transSaveData(principal), jwtProperties.getRefreshTokenValidityInSeconds());
        response.setHeader(HttpHeaders.AUTHORIZATION, jwtProperties.getPrefix() + " " + accessToken);
        response.addCookie(createCookie(refreshToken));
    }

    private String transSaveData(Principal principal) throws JsonProcessingException {
        return objectMapper.writeValueAsString(createUserInfo(principal));
    }

    private UserInfo createUserInfo(Principal principal) {
        return new UserInfo(principal.getUsername(), toTrans(principal.getAuthorities()));
    }

    private UsernamePasswordAuthenticationToken createAuthenticationToken(LoginDto login) {
        return new UsernamePasswordAuthenticationToken(
                login.getUsername(), login.getPassword());
    }

    private Cookie createCookie(String key) {
        Cookie cookie = new Cookie("Refresh", key);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/api/auth/reissue");
        cookie.setMaxAge(60 * jwtProperties.getRefreshTokenValidityInSeconds());
        return cookie;
    }

    private String toTrans(Collection<GrantedAuthority> list) {
        return StringUtils.collectionToCommaDelimitedString(list);
    }

    private LoginDto toTransDto(HttpServletRequest request) throws IOException {
        return objectMapper.readValue(request.getInputStream(), LoginDto.class);
    }
}
