package com.wanted.external.info;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class FacebookApiInfo implements SnsApiInfo {

    //@Value("${facebook.api.like.method}")
    private String likeMethod;

    //@Value("${facebook.api.like.endpoint}")
    private String likeEndpoint;

    //@Value("${facebook.api.key}")
    private String key;

    //@Value("${facebook.api.key.name}")
    private String keyName;
}
