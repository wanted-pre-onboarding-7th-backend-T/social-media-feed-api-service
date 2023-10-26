package com.wanted.content.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ContentLikeResponseDto {

    private Long contentId;

    private Long likeCount;
}
