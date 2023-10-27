package com.wanted.content.controller;

import com.wanted.common.dto.ResponseDto;
import com.wanted.content.dto.response.ContentLikeResponseDto;
import com.wanted.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contents")
public class ContentController {

    private final ContentService contentService;

    @PostMapping("/{contentId}/likes")
    public ResponseDto<ContentLikeResponseDto> increaseLikeCount(@PathVariable Long contentId) {
        Long likeCount = contentService.increaseLikeCount(contentId);
        return new ResponseDto<>(200, HttpStatus.OK.name(),
            new ContentLikeResponseDto(contentId, likeCount));
    }
}
