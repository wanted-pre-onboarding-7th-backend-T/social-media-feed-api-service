package com.wanted.content.entity;

import com.wanted.common.BaseTime;
import com.wanted.content.enums.SnsType;
import com.wanted.hashtag.entity.ContentHashtag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Content extends BaseTime {

    @Id
    @Column(name = "content_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_sns_id", nullable = false)
    private String contentSnsId;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SnsType type;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @OneToMany(mappedBy = "content", fetch = FetchType.LAZY)
    private List<ContentHashtag> hashtags = new ArrayList<>();

    @Column(name = "view_count", nullable = false)
    private Long viewCount;

    @Column(name = "like_count", nullable = false)
    private Long likeCount;

    @Column(name = "share_count", nullable = false)
    private Long shareCount;

    public void updateViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public void saveHashTags(List<ContentHashtag> contentHashtags) {
        this.hashtags = contentHashtags;
    }

    public void updateLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public void updateShareCount(Long shareCount) {
        this.shareCount = shareCount;
    }
}
