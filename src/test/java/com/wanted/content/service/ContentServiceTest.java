package com.wanted.content.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.wanted.common.dto.ResponseDto;
import com.wanted.common.exception.CommonException;
import com.wanted.content.dto.ContentsDetailsResponseDto;
import com.wanted.content.entity.Content;
import com.wanted.content.enums.SnsType;
import com.wanted.content.repository.ContentRepository;
import com.wanted.hashtag.entity.ContentHashtag;
import com.wanted.hashtag.entity.HashTag;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest {

    @Mock
    ContentRepository contentRepository;

    @InjectMocks
    ContentService contentService;

    @DisplayName("게시글 상세 정보 테스트 : 성공")
    @Test
    void findContentDetails_success() {
        // given
        Long contentId = 1L;
        Long viewCount = 0L;

        Content content = makeContent(contentId, viewCount);

        given(contentRepository.findById(contentId)).willReturn(Optional.of(content));

        // when
        ResponseDto<ContentsDetailsResponseDto> contentsDetailsResponseDtoResponseDto =
            contentService.findContentDetails(contentId);

        // then
        then(contentRepository).should(times(1)).findById(content.getId());
        assertThat(contentsDetailsResponseDtoResponseDto.getData().getViewCount()).isEqualTo(viewCount + 1);
    }

    @DisplayName("게시글 상세 정보 테스트 : 실패")
    @Test
    void findContentDetails_fail() {
        // given
        Long notExistContentId = 10L;
        given(contentRepository.findById(notExistContentId)).willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> contentService.findContentDetails(notExistContentId))
            .isInstanceOf(CommonException.class)
            .hasMessage("해당 게시물이 존재하지 않습니다..");
    }

    private Content makeContent(Long contentId, Long viewCount) {
        HashTag hashTag1 = new HashTag(1L, "hashtag1");
        HashTag hashTag2 = new HashTag(2L, "hashtag2");
        HashTag hashTag3 = new HashTag(3L, "hashtag3");

        Content content = Content.builder()
            .id(contentId)
            .contentSnsId("contentSnsId")
            .type(SnsType.FACEBOOK)
            .title("title" + contentId)
            .content("content" + contentId)
            .likeCount(0L)
            .viewCount(viewCount)
            .likeCount(0L)
            .shareCount(0L)
            .build();
        List<ContentHashtag> contentHashtagList = List.of(
            new ContentHashtag(1L, content, hashTag1),
            new ContentHashtag(2L, content, hashTag2),
            new ContentHashtag(3L, content, hashTag3));

        content.saveHashTags(contentHashtagList);
        return content;
    }
}