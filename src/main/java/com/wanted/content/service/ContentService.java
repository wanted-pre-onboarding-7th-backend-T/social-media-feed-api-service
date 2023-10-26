package com.wanted.content.service;

import com.wanted.common.dto.ResponseDto;
import com.wanted.common.exception.CommonException;
import com.wanted.content.dto.ContentsDetailsResponseDto;
import com.wanted.content.entity.Content;
import com.wanted.content.repository.ContentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    @Transactional
    public ResponseDto<ContentsDetailsResponseDto> findContentDetails(Long id) {
        if (contentRepository.findById(id).isPresent()) {
            contentRepository.increasingViewCount(id);
            Content content = contentRepository.findById(id).get();
            List<String> hashTags = content.getHashtags().stream()
                .map(contentHashtag -> contentHashtag.getHashTag().getName()).toList();
            ContentsDetailsResponseDto contentsDetailsResponseDto =
                ContentsDetailsResponseDto.toDto(content, hashTags);
            return new ResponseDto<>(200, "성공적으로 게시물을 가져왔습니다.", contentsDetailsResponseDto);
        } else {
            throw new CommonException(HttpStatus.NOT_FOUND, "해당 게시물이 존재하지 않습니다..");
        }
    }

}
