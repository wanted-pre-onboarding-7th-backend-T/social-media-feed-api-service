package com.wanted.content.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.wanted.common.exception.CommonException;
import com.wanted.content.entity.Content;
import com.wanted.content.enums.SnsType;
import com.wanted.content.repository.ContentRepository;
import com.wanted.external.service.SnsApiService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest {

    @InjectMocks
    ContentService contentService;

    @Mock
    SnsApiService snsApiService;

    @Mock
    ContentRepository contentRepository;

    @DisplayName("게시물 좋아요 테스트 : 성공")
    @Test
    void increaseLikeCountSuccess() {
        //given
        Long increasedLikeCount = 10L;
        Content content = Content.builder().id(1L).contentSnsId("abc")
            .type(SnsType.FACEBOOK).likeCount(5L).build();
        given(contentRepository.findById(anyLong())).willReturn(Optional.of(content));
        given(snsApiService.callLikeApi(any(Content.class))).willReturn(increasedLikeCount);

        //when
        contentService.increaseLikeCount(content.getId());

        //then
        then(contentRepository).should(times(1)).findById(content.getId());
        then(snsApiService).should(times(1)).callLikeApi(content);
        assertThat(content.getLikeCount()).isEqualTo(increasedLikeCount);
    }

    @DisplayName("게시물 엔티티 조회 테스트 : 실패")
    @Test
    void getContentFail() {
        //given
        given(contentRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> contentService.getContent(1L))
            .isInstanceOf(CommonException.class);
    }
}
