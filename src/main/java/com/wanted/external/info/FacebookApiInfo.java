package com.wanted.external.info;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class FacebookApiInfo implements SnsApiInfo {

    @Getter
    @Value("${facebook.api.endpoint:}")
    private String endpoint;

    @Value("#{${facebook.api.keys:{ : }}}")
    private MultiValueMap<String, String> keys;
}
