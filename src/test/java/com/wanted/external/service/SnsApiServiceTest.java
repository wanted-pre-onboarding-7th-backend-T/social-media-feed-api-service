package com.wanted.external.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.wanted.common.exception.CommonException;
import com.wanted.content.entity.Content;
import com.wanted.content.enums.SnsType;
import com.wanted.external.info.ApiSpec;
import com.wanted.external.info.SnsApiInfo;
import com.wanted.external.info.SnsApiInfoFactory;
import java.io.IOException;
import java.util.Optional;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;

@Import(SnsApiTestConfig.class)
@ExtendWith(SpringExtension.class)
public class SnsApiServiceTest {

    @Autowired
    SnsApiService snsApiService;

    @MockBean
    SnsApiInfoFactory snsApiInfoFactory;

    @MockBean
    SnsApiInfo snsApiInfo;

    static MockWebServer mockWebServer;

    String baseUrl;

    @BeforeAll
    static void initAll() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @BeforeEach
    void init() {
        baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
    }

    @AfterAll
    static void tearDownAll() throws IOException {
        mockWebServer.shutdown();
    }

    @DisplayName("외부 SNS 좋아요 API 호출 테스트 : 성공")
    @Test
    void callLikeApiSuccess() {
        //given
        Long increasedLikeCount = 100L;
        Long originLikeCount = 95L;
        Content content = Content.builder().id(1L).contentSnsId("abc")
            .type(SnsType.FACEBOOK).likeCount(originLikeCount).build();
        ApiSpec apiSpec = new ApiSpec(HttpMethod.GET, "/test", new LinkedMultiValueMap<>());
        given(snsApiInfoFactory.getSnsApiInfo(any(SnsType.class))).willReturn(snsApiInfo);
        given(snsApiInfo.getEndpoint()).willReturn(baseUrl);
        given(snsApiInfo.getLikeApiSpec(anyString())).willReturn(Optional.of(apiSpec));
        mockWebServer.enqueue(new MockResponse()
            .setBody(String.valueOf(increasedLikeCount))
            .setHeader("Content-Type", "application/json"));

        //when
        Long result = snsApiService.callLikeApi(content);

        //then
        assertThat(result).isEqualTo(increasedLikeCount);
    }

    @DisplayName("외부 SNS 좋아요 API 호출 테스트 : 실패 - 외부 API 응답 코드 에러")
    @Test
    void callLikeApiFail() {
        //given
        Long originLikeCount = 95L;
        Content content = Content.builder().id(1L).contentSnsId("abc")
            .type(SnsType.FACEBOOK).likeCount(originLikeCount).build();
        ApiSpec apiSpec = new ApiSpec(HttpMethod.GET, "/test", new LinkedMultiValueMap<>());
        given(snsApiInfoFactory.getSnsApiInfo(any(SnsType.class))).willReturn(snsApiInfo);
        given(snsApiInfo.getEndpoint()).willReturn(baseUrl);
        given(snsApiInfo.getLikeApiSpec(anyString())).willReturn(Optional.of(apiSpec));
        mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.BAD_REQUEST.value()));

        //when
        Long result = snsApiService.callLikeApi(content);

        //then
        assertThat(result).isEqualTo(originLikeCount);
    }

    @DisplayName("외부 SNS 좋아요 API 호출 테스트 : 실패 - 해당 API 존재X")
    @Test
    void callLikeApiFail2() {
        //given
        Long originLikeCount = 95L;
        Content content = Content.builder().id(1L).contentSnsId("abc")
            .type(SnsType.FACEBOOK).likeCount(originLikeCount).build();
        given(snsApiInfoFactory.getSnsApiInfo(any(SnsType.class))).willReturn(snsApiInfo);
        given(snsApiInfo.getEndpoint()).willReturn(baseUrl);
        given(snsApiInfo.getLikeApiSpec(anyString())).willReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> snsApiService.callLikeApi(content))
            .isInstanceOf(CommonException.class);
    }
}
