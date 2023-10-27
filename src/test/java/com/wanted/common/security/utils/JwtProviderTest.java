package com.wanted.common.security.utils;

import com.wanted.common.security.vo.Principal;
import com.wanted.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import javax.crypto.SecretKey;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import(JwtProvider.class)
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = JwtProperties.class)
class JwtProviderTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JwtProperties properties;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JwtProvider provider;
    private Principal principal;

    @BeforeEach
    void init() {
        User tester = User.builder()
                .email("test@gmail.com")
                .id(1L)
                .userName("tester")
                .password("1q2w3e4r5ty6")
                .build();
        principal = new Principal(tester, List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    @DisplayName("AccessToken 구현 테스트")
    void generate_access_token_test() throws Exception{
        // given
        String accessToken = provider.generateAccessToken(principal.getUsername(),principal.getId(),toTrans(principal.getAuthorities()));
        // when
        Claims payload = Jwts.parser()
                .verifyWith(getEncodedKey())
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(payload.getSubject()).isEqualTo(principal.getUsername());
            softAssertions.assertThat(payload.get("id",Long.class)).isEqualTo(principal.getId());
        });
    }

    @Test
    @DisplayName("refreshToken 구현 테스트")
    void generate_refresh_token_test() throws Exception{
        // given
        String refresh = provider.generateRefreshToken(principal.getUsername());
        // when
        // when
        Claims payload = Jwts.parser()
                .verifyWith(getEncodedKey())
                .build()
                .parseSignedClaims(refresh)
                .getPayload();
        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(payload.getSubject()).isEqualTo(principal.getUsername());
        });
    }

    private SecretKey getEncodedKey() {
        return Keys.hmacShaKeyFor(properties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    private String toTrans(Collection<GrantedAuthority> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(data -> sb.append(data).append(","));
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}