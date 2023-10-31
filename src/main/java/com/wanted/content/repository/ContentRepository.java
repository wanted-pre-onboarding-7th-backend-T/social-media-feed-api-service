package com.wanted.content.repository;

import com.wanted.content.entity.Content;
import com.wanted.content.enums.SnsType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContentRepository extends JpaRepository<Content, Long> {

    @Query("""
        select c
        from Content c
        where c.type = :snsType and c.title like %:search%
    """)
    Page<Content> findByTitleContainsQuery(String search, SnsType snsType, Pageable pageable);

    @Query("""
        select c
        from Content c
        where c.type = :snsType and c.content like %:search%
    """)
    Page<Content> findByContentContainsQuery(String search, SnsType snsType, Pageable pageable);

    @Query("""
        select c
        from Content c
        where c.type = :snsType and c.title like %:search% or c.content like %:search%
    """)
    Page<Content> findByTitleOrContentContainsQuery(String search, SnsType snsType, Pageable pageable);

    @Query("""
        select c
        from Content c
        where c.title like %:search%
    """)
    Page<Content> findByTitleContainsQuery(String search, Pageable pageable);

    @Query("""
        select c
        from Content c
        where c.content like %:search%
    """)
    Page<Content> findByContentContainsQuery(String search, Pageable pageable);

    @Query("""
        select c
        from Content c
        where c.title like %:search% or c.content like %:search%
    """)
    Page<Content> findByTitleOrContentContainsQuery(String search, Pageable pageable);

}
