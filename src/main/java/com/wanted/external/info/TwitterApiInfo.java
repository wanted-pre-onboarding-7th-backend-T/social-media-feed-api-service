package com.wanted.external.info;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TwitterApiInfo implements SnsApiInfo {

    //@Value("${twitter.api.like.method}")
    private String likeMethod;

    //@Value("${twitter.api.like.endpoint}")
    private String likeEndpoint;

    //@Value("${twitter.api.key}")
    private String key;

    //@Value("${twitter.api.key.name}")
    private String keyName;
}
