package com.wanted.external.info.impl;

import com.wanted.external.info.ApiSpec;
import com.wanted.external.info.SnsApiInfo;
import java.util.Optional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class ThreadApiInfo implements SnsApiInfo {

    @Getter
    @Value("${thread.api.endpoint:}")
    private String endpoint;

    @Value("#{${thread.api.keys:{ : }}}")
    private MultiValueMap<String, String> keys;

    @Override
    public Optional<ApiSpec> getLikeApiSpec(String contentSnsId) {
        return Optional.of(new ApiSpec(HttpMethod.POST, "/likes/" + contentSnsId, keys));
    }

    @Override
    public Optional<ApiSpec> getShareApiSpec(String contentSnsId) {
        return Optional.of(new ApiSpec(HttpMethod.POST, "/share/" + contentSnsId, keys));
    }
}
