package com.wanted.content.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class ContentSearchResponseDto extends ResponseListDto {

    private List<ContentsDetailsResponseDto> content;

}
