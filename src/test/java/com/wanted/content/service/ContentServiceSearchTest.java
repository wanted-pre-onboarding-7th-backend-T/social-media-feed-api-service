package com.wanted.content.service;


import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.content.dto.ContentSearchRequestDto;
import com.wanted.content.dto.ContentSearchResponseDto;
import com.wanted.content.dto.ContentsDetailsResponseDto;
import com.wanted.content.entity.Content;
import com.wanted.content.enums.SnsType;
import com.wanted.content.repository.ContentRepository;
import com.wanted.hashtag.entity.ContentHashtag;
import com.wanted.hashtag.entity.HashTag;
import com.wanted.hashtag.repository.ContentHashtagRepository;
import com.wanted.hashtag.repository.HashtagRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ContentServiceSearchTest {

    @Autowired
    ContentService contentService;

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    HashtagRepository hashtagRepository;

    @Autowired
    ContentHashtagRepository contentHashtagRepository;

    @DisplayName("게시글 검색 조건 테스트 - 해시태그 : 성공")
    @Transactional
    @Test
    void findContents_success() {
        String givenHashtag = "hashtag2";
        SnsType givenType = SnsType.FACEBOOK;
        String givenOrderBy = "createdAt";
        String givenSortBy = "ASC";
        String givenSearchBy = "title,content";
        String givenSearch = "";
        int givenPageCount = 10;
        int givenPage = 0;
        ContentSearchRequestDto request = ContentSearchRequestDto.builder()
            .hashtag(givenHashtag)
            .type(givenType)
            .sortBy(givenSortBy)
            .orderBy(givenOrderBy)
            .searchBy(givenSearchBy)
            .search(givenSearch)
            .pageCount(givenPageCount)
            .page(givenPage)
            .build();
        ContentSearchResponseDto contents = contentService.findContents(request);
        System.out.println("사이즈 : " + contents.getContent().size());
        Long id = 1L;
        for (ContentsDetailsResponseDto detailsResponseDto : contents.getContent()) {
            assertThat(detailsResponseDto.getId()).isEqualTo(id);
            assertThat(detailsResponseDto.getHashtags()).contains(givenHashtag);
            assertThat(detailsResponseDto.getType()).isEqualTo(givenType);
            assertThat(detailsResponseDto.getTitle()).isEqualTo("title" + id);
            assertThat(detailsResponseDto.getContent()).isEqualTo("content" + id);
            id += 2;
        }
    }

    @BeforeEach
    public void makeContent() {

        List<HashTag> hashTagList = List.of(
            new HashTag(1L, "hashtag1"),
            new HashTag(2L, "hashtag2"),
            new HashTag(3L, "hashtag3"),
            new HashTag(4L, "hashtag4"),
            new HashTag(5L, "hashtag5"),
            new HashTag(6L, "hashtag6"),
            new HashTag(7L, "hashtag7"),
            new HashTag(8L, "hashtag8"),
            new HashTag(9L, "hashtag9"),
            new HashTag(10L, "hashtag10")
        );

        hashtagRepository.saveAll(hashTagList);

        Long x = 0L;
        for (Long i = 1L; i <= 10L; i++) {
            Content content = Content.builder()
                .id(i)
                .contentSnsId("contentSnsId")
                .type(SnsType.FACEBOOK)
                .title("title" + i)
                .content("content" + i)
                .likeCount(i % 10)
                .viewCount(i)
                .likeCount(i % 10)
                .shareCount(i % 10)
                .build();
            contentRepository.save(content);
            List<ContentHashtag> contentHashtagList = new ArrayList<>();
            if (i % 2 == 1) {
                contentHashtagList.addAll(List.of(
                    new ContentHashtag(x++, content, hashTagList.get(0)),
                    new ContentHashtag(x++, content, hashTagList.get(1)),
                    new ContentHashtag(x++, content, hashTagList.get(2)),
                    new ContentHashtag(x++, content, hashTagList.get(3)),
                    new ContentHashtag(x++, content, hashTagList.get(4))
                ));
            } else {
                contentHashtagList.addAll(List.of(
                    new ContentHashtag(x++, content, hashTagList.get(5)),
                    new ContentHashtag(x++, content, hashTagList.get(6)),
                    new ContentHashtag(x++, content, hashTagList.get(7)),
                    new ContentHashtag(x++, content, hashTagList.get(8)),
                    new ContentHashtag(x++, content, hashTagList.get(9))
                ));
            }

            contentHashtagRepository.saveAll(contentHashtagList);
            content.saveHashTags(contentHashtagList);
            contentRepository.save(content);
        }
    }
}
