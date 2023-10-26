package com.wanted.content.repository;

import com.wanted.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ContentRepository extends JpaRepository<Content, Long> {

    @Modifying
    @Query("update Content c set c.viewCount = c.viewCount + 1 where c.id = :id")
    void increasingViewCount(Long id);
}
