package com.wanted.user.controller;

import com.wanted.common.dto.ResponseDto;
import com.wanted.user.dto.request.UserPostRequestDto;
import com.wanted.user.dto.response.UserIdResponseDto;
import com.wanted.user.service.UserService;
import com.wanted.user.utils.UriCreator;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private static final String URL = "/api/users";
    private final UserService service;

    @PostMapping
    public ResponseEntity<ResponseDto<UserIdResponseDto>> postUser(
            @RequestBody @Valid UserPostRequestDto post) {
        ResponseDto<UserIdResponseDto> result = service.saveUser(post);
        URI location = UriCreator.createUri(URL, result.getData().getUserId());
        return ResponseEntity.created(location).body(result);
    }

}
