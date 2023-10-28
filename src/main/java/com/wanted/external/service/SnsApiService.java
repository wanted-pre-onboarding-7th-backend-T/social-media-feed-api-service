package com.wanted.external.service;

import com.wanted.common.exception.CommonException;
import com.wanted.content.enums.SnsType;
import com.wanted.external.info.ApiSpec;
import com.wanted.external.info.SnsApiInfo;
import com.wanted.external.info.SnsApiInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SnsApiService {

    private final SnsApiInfoFactory snsApiInfoFactory;

    public Mono<Long> callLikeApi(String contentSnsId, SnsType type) {
        SnsApiInfo snsApiInfo = snsApiInfoFactory.getSnsApiInfo(type);
        ApiSpec likeApiSpec = snsApiInfo.getLikeApiSpec(contentSnsId)
            .orElseThrow(() -> new CommonException(HttpStatus.BAD_REQUEST, "해당 API가 존재하지 않습니다."));
        return WebClient.create(snsApiInfo.getEndpoint())
            .method(likeApiSpec.getMethod())
            .uri(uriBuilder -> uriBuilder.path(likeApiSpec.getPath())
                .queryParams(likeApiSpec.getQueryParams())
                .build())
            .retrieve()
            .bodyToMono(Long.class); //증가된 좋아요 개수 반환한다고 가정
    }
}
