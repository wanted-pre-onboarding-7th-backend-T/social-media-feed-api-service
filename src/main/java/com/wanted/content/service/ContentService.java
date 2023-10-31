package com.wanted.content.service;

import com.wanted.common.dto.ResponseDto;
import com.wanted.common.exception.CommonException;
import com.wanted.content.dto.response.ContentLikeResponseDto;
import com.wanted.content.dto.response.ContentShareResponseDto;
import com.wanted.content.entity.Content;
import com.wanted.content.repository.ContentRepository;
import com.wanted.external.service.SnsApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final SnsApiService snsApiService;
    private final ContentRepository contentRepository;

    @Transactional
    public ResponseDto<ContentLikeResponseDto> increaseLikeCount(Long contentId) {
        Content content = getContent(contentId);
        Long increasedLikeCount = snsApiService.callLikeApi(content);
        content.updateLikeCount(increasedLikeCount);
        return new ResponseDto<>(200, "성공적으로 게시물을 좋아요 하였습니다.",
            new ContentLikeResponseDto(contentId, increasedLikeCount));
    }

    @Transactional
    public ResponseDto<ContentShareResponseDto> increaseShareCount(Long contentId) {
        Content content = getContent(contentId);
        Long increasedShareCount = snsApiService.callShareApi(content);
        content.updateShareCount(increasedShareCount);
        return new ResponseDto<>(200, "성공적으로 게시물을 공유하였습니다.",
            new ContentShareResponseDto(contentId, increasedShareCount));
    }

    @Transactional(readOnly = true)
    public Content getContent(Long contentId) {
        return contentRepository.findById(contentId)
            .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, "해당 게시물이 존재하지 않습니다."));
    }
}
