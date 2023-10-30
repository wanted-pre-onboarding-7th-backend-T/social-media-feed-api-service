package com.wanted.content.service;

import com.wanted.common.PagingUtil;
import com.wanted.common.dto.ResponseDto;
import com.wanted.common.exception.CommonException;
import com.wanted.content.dto.ContentSearchRequestDto;
import com.wanted.content.dto.ContentSearchResponseDto;
import com.wanted.content.dto.ContentsDetailsResponseDto;
import com.wanted.content.entity.Content;
import com.wanted.content.repository.ContentRepository;
import com.wanted.hashtag.entity.ContentHashtag;
import com.wanted.hashtag.repository.ContentHashtagRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;
    private final ContentHashtagRepository contentHashtagRepository;

    @Transactional
    public ResponseDto<ContentsDetailsResponseDto> findContentDetails(Long id) {
        Content content = contentRepository.findById(id)
            .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, "해당 게시물이 존재하지 않습니다.."));
        content.updateViewCount(content.getViewCount() + 1);
        List<String> hashTags = content.getHashtags().stream()
            .map(contentHashtag -> contentHashtag.getHashTag().getName()).toList();
        ContentsDetailsResponseDto contentsDetailsResponseDto =
            ContentsDetailsResponseDto.toDto(content, hashTags);
        return new ResponseDto<>(200, "성공적으로 게시물을 가져왔습니다.", contentsDetailsResponseDto);
    }

    public ContentSearchResponseDto findContents(
        ContentSearchRequestDto request) {
        if (request.getHashtag() == null) {
            request.setHashtag("본인계정"); // TODO: 본인계정을 해시태그에 넣어주기.
        }

        PageRequest pageRequest = PageRequest.of(
            request.getPage(), request.getPageCount(),
            Sort.by(Direction.fromString(request.getSortBy()), request.getOrderBy()));

        Page<Content> searchResult;
        if (request.getType() == null) {
            searchResult = searchContentsNoSnsType(request, pageRequest);
        } else {
            searchResult = searchContentWithSnsType(request, pageRequest);
        }

        List<ContentsDetailsResponseDto> detailsResponseDto = new ArrayList<>();
        for (Content content : searchResult) {
            List<ContentHashtag> contentHashtag = contentHashtagRepository.findByContent(content);
            List<String> stringContentHashtagList = contentHashtag.stream()
                .map(s -> s.getHashTag().getName())
                .toList();
            if (stringContentHashtagList.contains(request.getHashtag())) {
                detailsResponseDto.add(
                    ContentsDetailsResponseDto.toDto(content, stringContentHashtagList));
            }
        }

        return ContentSearchResponseDto.builder()
            .pagingUtil(PagingUtil.getPagingUtil(searchResult))
            .content(detailsResponseDto)
            .build();
    }

    private Page<Content> searchContentWithSnsType(ContentSearchRequestDto request,
        PageRequest pageRequest) {
        Page<Content> searchResult;
        if (request.getSearchBy().equals("title")) {
            searchResult = contentRepository.findByTitleContainsQuery(
                request.getSearch(), request.getType(), pageRequest);
        } else if (request.getSearchBy().equals("content")) {
            searchResult = contentRepository.findByContentContainsQuery(
                request.getSearch(), request.getType(), pageRequest);
        } else {
            searchResult = contentRepository.findByTitleOrContentContainsQuery(
                request.getSearch(), request.getType(), pageRequest);
        }
        return searchResult;
    }

    private Page<Content> searchContentsNoSnsType(ContentSearchRequestDto request,
        PageRequest pageRequest) {
        Page<Content> searchResult;
        if (request.getSearchBy().equals("title")) {
            searchResult = contentRepository.findByTitleContainsQuery(
                request.getSearch(), pageRequest);
        } else if (request.getSearchBy().equals("content")) {
            searchResult = contentRepository.findByContentContainsQuery(
                request.getSearch(), pageRequest);
        } else {
            searchResult = contentRepository.findByTitleOrContentContainsQuery(
                request.getSearch(), pageRequest);
        }
        return searchResult;
    }
}
