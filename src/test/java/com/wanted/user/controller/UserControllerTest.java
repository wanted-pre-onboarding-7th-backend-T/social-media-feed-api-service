package com.wanted.user.controller;

import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.common.config.SecurityConfig;
import com.wanted.common.security.config.AuthTestConfig;
import com.wanted.user.dto.request.UserPostRequestDto;
import com.wanted.user.mock.UserMock;
import com.wanted.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
@Import({AuthTestConfig.class, SecurityConfig.class})
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserMock userMock;
    @MockBean
    UserService service;

    @Test
    @DisplayName("회원 가입 테스트 : 성공")
    void post_user_success_test() throws Exception {
        // given
        UserPostRequestDto post = userMock.postMock();
        String content = objectMapper.writeValueAsString(post);

        BDDMockito.given(service.saveUser(any(UserPostRequestDto.class)))
                .willReturn(userMock.postResponseMock());
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post("/api/users").content(content)
                        .contentType(MediaType.APPLICATION_JSON));
        // then
        perform
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeStamp").isString());
    }

    @Test
    @DisplayName("회원 가입 테스트 : 실패 [username 과 password 가 일치할 때]")
    void post_user_fail_password_wrong_test() throws Exception {
        // given
        UserPostRequestDto post = userMock.postMockPasswordSameWithUsername();
        String content = objectMapper.writeValueAsString(post);

        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post("/api/users").content(content)
                        .contentType(MediaType.APPLICATION_JSON));
        // then
        perform
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("회원 가입 테스트 : 실패 [password 3단어 이상 연속]")
    void post_user_fail_password_same_three_word_test() throws Exception {
        // given
        UserPostRequestDto post = userMock.postMockPasswordSameThreeWordPassword();
        String content = objectMapper.writeValueAsString(post);

        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post("/api/users").content(content)
                        .contentType(MediaType.APPLICATION_JSON));
        // then
        perform
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("회원 가입 테스트 : 실패 [password 10자리 이하]")
    void post_user_fail_password_not_enough_length_test() throws Exception {
        // given
        UserPostRequestDto post = userMock.postMockPasswordNotEnoughLength();
        String content = objectMapper.writeValueAsString(post);

        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post("/api/users").content(content)
                        .contentType(MediaType.APPLICATION_JSON));
        // then
        perform
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("회원 가입 테스트 : 실패 [password 단어 종류 1가지]")
    void post_user_fail_password_not_two_type_test() throws Exception {
        // given
        UserPostRequestDto post = userMock.postMockPasswordNotTwoKindOfWordPassword();
        String content = objectMapper.writeValueAsString(post);

        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post("/api/users").content(content)
                        .contentType(MediaType.APPLICATION_JSON));
        // then
        perform
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}
