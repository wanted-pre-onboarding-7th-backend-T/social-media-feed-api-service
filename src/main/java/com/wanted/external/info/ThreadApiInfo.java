package com.wanted.external.info;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ThreadApiInfo implements SnsApiInfo {

    //@Value("${thread.api.like.endpoint}")
    private String likeEndpoint;

    //@Value("${thread.api.key}")
    private String key;

    //@Value("${thread.api.key.name}")
    private String keyName;
}
