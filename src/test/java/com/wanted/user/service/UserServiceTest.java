package com.wanted.user.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.wanted.common.exception.CommonException;
import com.wanted.user.config.UserTestConfig;
import com.wanted.user.dto.request.UserPostRequestDto;
import com.wanted.user.dto.response.UserIdResponseDto;
import com.wanted.user.entity.User;
import com.wanted.user.mock.UserMock;
import com.wanted.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(UserTestConfig.class)
class UserServiceTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    UserService userService;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    UserMock userMock;
    @MockBean
    UserRepository repository;

    @Test
    @DisplayName("회원 가입 테스트 : 성공")
    void save_user_success_test() throws Exception {
        // given
        UserPostRequestDto post = userMock.postRequestMock();
        User afterRepository = userMock.entityMock();

        given(repository.save(any(User.class))).willReturn(afterRepository);
        given(repository.findByUserName(anyString())).willReturn(Optional.empty());

        // when
        UserIdResponseDto result = userService.saveUser(post);

        // then
        assertThat(result.getUserId()).isEqualTo(userMock.getUserId());
    }

    @Test
    @DisplayName("회원 가입 테스트 : 실패 [이미 존재 하는 username]")
    void save_user_fail_user_already_exist_test() throws Exception {
        // given
        UserPostRequestDto post = userMock.postRequestMock();
        User afterRepository = userMock.entityMock();

        given(repository.findByUserName(anyString())).willReturn(Optional.of(afterRepository));

        // when
        // then
        assertThatThrownBy(() -> userService.saveUser(post)).isInstanceOf(CommonException.class)
                .hasMessage("사용할 수 없는 username 입니다.");
    }


}