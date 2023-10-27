package com.wanted.external.info;

public interface SnsApiInfo {

    String getEndpoint();

    ApiSpec getLikeApiSpec(String contentSnsId);
}
