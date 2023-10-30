package com.wanted.content.dto;

import com.wanted.common.PagingUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder
public class ResponseListDto {
    private PagingUtil pagingUtil;
}
