package com.wanted.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanted.content.entity.Content;
import com.wanted.content.enums.SnsType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ContentsDetailsResponseDto {

    @JsonProperty("contents_id")
    private Long id;

    @JsonProperty("content_sns_id")
    private String contentSnsId;

    @Enumerated(EnumType.STRING)
    @JsonProperty("type")
    private SnsType type;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("hashtags")
    private List<String> hashtags;

    @JsonProperty("view_count")
    private Long viewCount;

    @JsonProperty("like_count")
    private Long likeCount;

    @JsonProperty("share_count")
    private Long shareCount;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public static ContentsDetailsResponseDto toDto(Content content, List<String> hashTags) {
        return ContentsDetailsResponseDto.builder()
            .id(content.getId())
            .contentSnsId(content.getContentSnsId())
            .type(content.getType())
            .title(content.getTitle())
            .content(content.getContent())
            .hashtags(hashTags)
            .viewCount(content.getViewCount())
            .likeCount(content.getLikeCount())
            .shareCount(content.getShareCount())
            .updatedAt(content.getUpdatedAt())
            .createdAt(content.getCreatedAt())
            .build();
    }
}
