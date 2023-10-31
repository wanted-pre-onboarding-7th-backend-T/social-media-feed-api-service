package com.wanted.external.info;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

@Getter
@RequiredArgsConstructor
public class ApiSpec {

    private final HttpMethod method;

    private final String path;

    private final MultiValueMap<String, String> queryParams;
}
