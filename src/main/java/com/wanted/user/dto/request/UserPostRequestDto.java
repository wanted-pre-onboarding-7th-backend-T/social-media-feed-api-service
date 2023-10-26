package com.wanted.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPostRequestDto {
    @NotNull(message = "계정 이름은 빈 값 일 수 없 습니다.")
    private String userName;
    @NotNull(message = "이메일은 빈 값일 수 없 습니다.")
    @Email(message = "이메일 형식이 틀렸습니다.")
    private String email;

    @Setter
    private String password;
}
