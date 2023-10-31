package com.wanted.external.service;

import com.wanted.external.info.SnsApiInfo;
import com.wanted.external.info.SnsApiInfoFactory;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class SnsApiTestConfig {

    @Bean
    public SnsApiService snsApiService() {
        return new SnsApiService(snsApiInfoFactory());
    }

    @Bean
    public SnsApiInfoFactory snsApiInfoFactory() {
        return Mockito.mock(SnsApiInfoFactory.class);
    }

    @Bean
    public SnsApiInfo snsApiInfo() {
        return Mockito.mock(SnsApiInfo.class);
    }
}
