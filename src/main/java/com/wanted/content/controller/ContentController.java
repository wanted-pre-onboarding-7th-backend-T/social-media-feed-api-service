package com.wanted.content.controller;

import com.wanted.common.dto.ResponseDto;
import com.wanted.content.dto.ContentsDetailsResponseDto;
import com.wanted.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contents")
public class ContentController {

    private final ContentService contentService;

    @GetMapping("/{id}")
    public ResponseDto<ContentsDetailsResponseDto> getContentsDetails(@PathVariable Long id) {
        return contentService.findContentDetails(id);
    }
}
