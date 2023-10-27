package com.wanted.common.redis.repository;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataRedisTest
@MockBean(JpaMetamodelMappingContext.class)
@Import({RedisRepository.class})
class RedisRepositoryTest {
    @Autowired
    RedisRepository repository;

    @Test
    @DisplayName("레디스 저장 테스트")
    void save_test() throws Exception{
        // given
        String key = "Test";
        String value = "Test";
        repository.save(key, value,10);
        // when
        String result = repository.findByKey(key);
        // then
        assertThat(result).isEqualTo(value);
    }

    @Test
    @DisplayName("레디스 삭제 테스트")
    void delete_test() throws Exception{
        // given
        String key = "Test001";
        String value = "Test";
        // when
        repository.save(key,value,10);
        repository.delete(key);
        String result = repository.findByKey(key);
        // then
        assertThat(result).isNull();
    }
}