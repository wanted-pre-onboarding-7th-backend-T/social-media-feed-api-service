package com.wanted.common.security.controller;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.common.config.SecurityConfig;
import com.wanted.common.redis.repository.RedisRepository;
import com.wanted.common.security.config.AuthTestConfig;
import com.wanted.common.security.dto.LoginDto;
import com.wanted.user.entity.User;
import com.wanted.user.mock.UserMock;
import com.wanted.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(AuthController.class)
@Import({AuthTestConfig.class, SecurityConfig.class})
@MockBean(JpaMetamodelMappingContext.class)
class AuthControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserMock userMock;
    @MockBean
    UserRepository repository;

    @MockBean
    RedisRepository redis;
    @Test
    @DisplayName("로그인 테스트 : 성공")
    void login_success_test() throws Exception{
        // given
        LoginDto login = userMock.loginMock();
        User mockEntity = userMock.entityMock();
        String content = objectMapper.writeValueAsString(login);

        given(repository.findByUserName(anyString())).willReturn(Optional.of(mockEntity));

        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post("/api/login").content(content)
                        .contentType(MediaType.APPLICATION_JSON));
        // then
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.AUTHORIZATION))
                .andExpect(MockMvcResultMatchers.cookie().exists("Refresh"));
    }
    @Test
    @DisplayName("로그인 테스트 : 실패")
    void login_failure_test() throws Exception{
        // given
        LoginDto login = userMock.wrongLoginMock();
        User mockEntity = userMock.entityMock();
        String content = objectMapper.writeValueAsString(login);

        given(repository.findByUserName(anyString())).willReturn(Optional.of(mockEntity));

        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post("/api/login").content(content)
                        .contentType(MediaType.APPLICATION_JSON));
        // then
        perform
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}