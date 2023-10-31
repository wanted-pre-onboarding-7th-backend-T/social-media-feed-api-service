package com.wanted.external.info;

import java.util.Optional;

public interface SnsApiInfo {

    String getEndpoint();

    default Optional<ApiSpec> getLikeApiSpec(String contentSnsId) {
        return Optional.empty();
    }
}
