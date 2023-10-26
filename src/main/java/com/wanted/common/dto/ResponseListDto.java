package com.wanted.common.dto;

import com.wanted.common.PagingUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class ResponseListDto {

    private PagingUtil pagingUtil;
}
