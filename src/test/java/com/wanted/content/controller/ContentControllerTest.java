package com.wanted.content.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.wanted.common.config.SecurityConfig;
import com.wanted.common.dto.ResponseDto;
import com.wanted.content.dto.response.ContentLikeResponseDto;
import com.wanted.content.dto.response.ContentShareResponseDto;
import com.wanted.content.service.ContentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WithMockUser
@Import(SecurityConfig.class)
@WebMvcTest(ContentController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ContentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ContentService contentService;

    @DisplayName("게시물 좋아요 테스트 : 성공")
    @Test
    void increaseLikeCountSuccess() throws Exception {
        //given
        Long contentId = 1L;
        Long likeCount = 10L;
        ContentLikeResponseDto data = new ContentLikeResponseDto(contentId, likeCount);
        given(contentService.increaseLikeCount(anyLong())).willReturn(
            new ResponseDto<>(200, HttpStatus.OK.name(), data));

        //when
        ResultActions perform = mockMvc.perform(
            post("/api/contents/{contentId}/likes", contentId)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.contentId").value(contentId))
            .andExpect(jsonPath("$.data.likeCount").value(likeCount))
            .andExpect(jsonPath("$.code").isNumber())
            .andExpect(jsonPath("$.message").isString())
            .andExpect(jsonPath("$.timeStamp").isString());
    }

    @DisplayName("게시물 공유하기 테스트 : 성공")
    @Test
    void increaseShareCountSuccess() throws Exception {
        //given
        Long contentId = 1L;
        Long shareCount = 10L;
        ContentShareResponseDto data = new ContentShareResponseDto(contentId, shareCount);
        given(contentService.increaseShareCount(anyLong())).willReturn(
            new ResponseDto<>(200, HttpStatus.OK.name(), data));

        //when
        ResultActions perform = mockMvc.perform(
            post("/api/contents/{contentId}/share", contentId)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.contentId").value(contentId))
            .andExpect(jsonPath("$.data.shareCount").value(shareCount))
            .andExpect(jsonPath("$.code").isNumber())
            .andExpect(jsonPath("$.message").isString())
            .andExpect(jsonPath("$.timeStamp").isString());
    }
}
