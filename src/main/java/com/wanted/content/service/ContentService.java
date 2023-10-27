package com.wanted.content.service;

import com.wanted.common.exception.CommonException;
import com.wanted.content.entity.Content;
import com.wanted.content.enums.SnsType;
import com.wanted.content.repository.ContentRepository;
import com.wanted.external.service.SnsApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final SnsApiService snsApiService;
    private final ContentRepository contentRepository;

    public Long increaseLikeCount(Long contentId) {
        Content content = getContent(contentId);
        SnsType type = content.getType();
        snsApiService.callLikeApi(contentId, type); //외부 API 호출
        return content.getLikeCount() + 1;
    }

    private Content getContent(Long contentId) {
        return contentRepository.findById(contentId)
            .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, "해당 게시물이 존재하지 않습니다."));
    }
}
