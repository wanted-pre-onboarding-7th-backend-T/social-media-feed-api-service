package com.wanted.content.controller;

import com.wanted.common.dto.ResponseDto;
import com.wanted.content.dto.ContentSearchResponseDto;
import com.wanted.content.dto.ContentsDetailsResponseDto;
import com.wanted.content.dto.ContentSearchRequestDto;
import com.wanted.content.dto.response.ContentLikeResponseDto;
import com.wanted.content.dto.response.ContentShareResponseDto;
import com.wanted.content.service.ContentService;
import com.wanted.content.validation.annotation.NumberIdValid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contents")
public class ContentController {

    private final ContentService contentService;

    @GetMapping("/{id}")
    public ResponseDto<ContentsDetailsResponseDto> getContentsDetails(@PathVariable Long id) {
        return contentService.findContentDetails(id);
    }

    @GetMapping
    public ContentSearchResponseDto getContents(@ModelAttribute ContentSearchRequestDto searchRequest) {
        return contentService.findContents(searchRequest);
    }

    @PostMapping("/{contentId}/likes")
    public ResponseEntity<ResponseDto<ContentLikeResponseDto>> increaseLikeCount(
        @NumberIdValid @PathVariable Long contentId) {
        ResponseDto<ContentLikeResponseDto> result = contentService.increaseLikeCount(contentId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{contentId}/share")
    public ResponseEntity<ResponseDto<ContentShareResponseDto>> increaseShareCount(
        @NumberIdValid @PathVariable Long contentId) {
        ResponseDto<ContentShareResponseDto> result = contentService.increaseShareCount(contentId);
        return ResponseEntity.ok(result);
    }
}
