package com.wanted.content.dto;

import com.wanted.content.enums.SnsType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentSearchRequestDto {

    private String hashtag;

    @Enumerated(EnumType.STRING)
    private SnsType type;

    private String orderBy = "createdAt";

    private String sortBy = "ASC";

    private String searchBy = "title,content";

    private String search = "";

    private int pageCount = 10;

    private int page = 0;

    @Override
    public String toString() {
        return "ContentSearchRequestDto{" +
            "hashtag='" + hashtag + '\'' +
            ", type=" + type +
            ", orderBy='" + orderBy + '\'' +
            ", searchBy='" + searchBy + '\'' +
            ", search='" + search + '\'' +
            ", pageCount=" + pageCount +
            ", page=" + page +
            '}';
    }
}
