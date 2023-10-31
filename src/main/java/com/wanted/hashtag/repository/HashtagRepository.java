package com.wanted.hashtag.repository;

import com.wanted.hashtag.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<HashTag, Long> {

}
