package com.wanted.external.info;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class InstagramApiInfo implements SnsApiInfo {

    //@Value("${instagram.api.like.endpoint}")
    private String likeEndpoint;

    //@Value("${instagram.api.key}")
    private String key;

    //@Value("${instagram.api.key.name}")
    private String keyName;
}
