package com.suman.notedown.exception;

import com.suman.notedown.dto.errorDtos.ErrorResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    public JwtAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.out.println("Inside JwtAccessDeniedHandler :: handle");
        System.out.println(accessDeniedException.getClass().getName());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(response.getStatus(), accessDeniedException.getMessage());
        String json = objectMapper.writeValueAsString(errorResponseDTO);
        response.getWriter().write(json);
    }
}
