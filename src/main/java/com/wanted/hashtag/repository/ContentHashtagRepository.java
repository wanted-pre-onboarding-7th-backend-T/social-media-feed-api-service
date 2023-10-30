package com.wanted.hashtag.repository;

import com.wanted.content.entity.Content;
import com.wanted.hashtag.entity.ContentHashtag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentHashtagRepository extends JpaRepository<ContentHashtag, Long> {

    List<ContentHashtag> findByContent(Content content);
}
