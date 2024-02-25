package com.colak.springoauth2resourceserverjwttutorial.config.security.accessdenied;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.OutputStream;


@Slf4j
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper;


    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        log.error("Unauthorized Error : {}", authException.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiErrorResponseDto apiErrorResponseDto = new ApiErrorResponseDto(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Access denied: Authorization header is required.");
        OutputStream out = response.getOutputStream();
        objectMapper.writeValue(out, apiErrorResponseDto);
        out.flush();

        // This sends "text/html" response
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
