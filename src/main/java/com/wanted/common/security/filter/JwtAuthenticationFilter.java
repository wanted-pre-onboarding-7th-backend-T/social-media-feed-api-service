package com.wanted.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.common.redis.repository.RedisRepository;
import com.wanted.common.security.dto.LoginDto;
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

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final RedisRepository repository;

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
            Authentication authResult) {
        Principal principal = (Principal) authResult.getPrincipal();

        String accessToken =
                jwtProvider.generateAccessToken(principal.getUsername(), principal.getId(),
                        toTrans(principal.getAuthorities()));
        String refreshToken = jwtProvider.generateRefreshToken(principal.getUsername());

        repository.save(refreshToken, principal.getUsername(), 200);
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        response.addCookie(createCookie(refreshToken));
    }

    private LoginDto toTransDto(HttpServletRequest request) throws IOException {
        return objectMapper.readValue(request.getInputStream(), LoginDto.class);
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
        cookie.setMaxAge(60 * 200);
        return cookie;
    }

    private String toTrans(Collection<GrantedAuthority> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(data -> sb.append(data).append(","));
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
