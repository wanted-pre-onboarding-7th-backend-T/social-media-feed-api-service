package com.wanted.content.service;

import com.wanted.common.dto.ResponseDto;
import com.wanted.common.exception.CommonException;
import com.wanted.content.dto.response.ContentLikeResponseDto;
import com.wanted.content.entity.Content;
import com.wanted.content.enums.SnsType;
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

    public ResponseDto<ContentLikeResponseDto> increaseLikeCount(Long contentId) {
        Content content = getContent(contentId);
        String contentSnsId = content.getContentSnsId();
        SnsType type = content.getType();
        snsApiService.callLikeApi(contentSnsId, type); //외부 API 호출
        Long increasedLikeCount = content.getLikeCount() + 1;
        return new ResponseDto<>(200, HttpStatus.OK.name(),
            new ContentLikeResponseDto(contentId, increasedLikeCount));
    }

    @Transactional(readOnly = true)
    public Content getContent(Long contentId) {
        return contentRepository.findById(contentId)
            .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, "해당 게시물이 존재하지 않습니다."));
    }
}
