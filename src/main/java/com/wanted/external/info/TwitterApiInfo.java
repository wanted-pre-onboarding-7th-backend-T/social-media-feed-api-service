package com.wanted.external.info;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class TwitterApiInfo implements SnsApiInfo {

    @Getter
    @Value("${twitter.api.endpoint:}")
    private String endpoint;

    @Value("#{${twitter.api.keys:{ : }}}")
    private MultiValueMap<String, String> keys;
}
