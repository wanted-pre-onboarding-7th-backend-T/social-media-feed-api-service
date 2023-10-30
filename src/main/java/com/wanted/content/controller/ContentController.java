package com.wanted.content.controller;

import com.wanted.common.dto.ResponseDto;
import com.wanted.content.dto.response.ContentLikeResponseDto;
import com.wanted.content.dto.response.ContentShareResponseDto;
import com.wanted.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseDto<ContentLikeResponseDto>> increaseLikeCount(@PathVariable Long contentId) {
        ResponseDto<ContentLikeResponseDto> result = contentService.increaseLikeCount(contentId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{contentId}/share")
    public ResponseEntity<ResponseDto<ContentShareResponseDto>> increaseShareCount(@PathVariable Long contentId) {
        ResponseDto<ContentShareResponseDto> result = contentService.increaseShareCount(contentId);
        return ResponseEntity.ok(result);
    }
}
