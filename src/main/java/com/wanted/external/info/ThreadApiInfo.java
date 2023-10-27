package com.wanted.external.info;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class ThreadApiInfo implements SnsApiInfo {

    @Getter
    @Value("${thread.api.endpoint:}")
    private String endpoint;

    @Value("#{${thread.api.keys:{ : }}}")
    private MultiValueMap<String, String> keys;
}
