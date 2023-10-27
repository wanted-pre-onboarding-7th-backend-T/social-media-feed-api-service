package com.wanted.common.security.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.common.security.enums.AuthExceptionCode;
import com.wanted.common.security.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        sendError(response, getExceptionCodeByRequest(request));
    }

    private AuthExceptionCode getExceptionCodeByRequest(HttpServletRequest request) {
        if (!(request.getAttribute("exceptionCode") instanceof AuthExceptionCode)) {
            return AuthExceptionCode.UNAUTHENTICATED;
        }
        return (AuthExceptionCode) request.getAttribute("exceptionCode");
    }

    private void sendError(HttpServletResponse response, AuthExceptionCode authExceptionCode)
            throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setStatus(authExceptionCode.getHttpStatus().value());
        response.getWriter().write(getResponseData(authExceptionCode));
    }

    private String getResponseData(AuthExceptionCode authExceptionCode) throws JsonProcessingException {
        return objectMapper.writeValueAsString(createErrorResponse(authExceptionCode));
    }

    private ErrorResponse createErrorResponse(AuthExceptionCode authExceptionCode) {
        return ErrorResponse.of(authExceptionCode.getHttpStatus(), authExceptionCode.getMessage());
    }
}


