package com.wanted.external.info;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class InstagramApiInfo implements SnsApiInfo {

    @Getter
    @Value("${instagram.api.endpoint:}")
    private String endpoint;

    @Value("#{${instagram.api.keys:{ : }}}")
    private MultiValueMap<String, String> keys;
}
