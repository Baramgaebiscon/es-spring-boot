package com.early.stage.demo.global.util;

import com.early.stage.demo.global.error.ErrorCase;
import com.early.stage.demo.global.error.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> String jsonFrom(T obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T objectOf(ServletInputStream inputStream, Class<T> type)
        throws IOException {
        return objectMapper.readValue(inputStream, type);
    }

    public static void setContentTypeToJson(HttpServletResponse response) {
        response.setContentType("application/json");
    }

    public static void setResponseToErrorResponse(HttpServletResponse response, ErrorCase errorCase)
        throws IOException {
        setContentTypeToJson(response);
        response.setStatus(errorCase.getStatus().value());
        String body = jsonFrom(new ErrorResponse(errorCase.getCode(), errorCase.getMessage()));
        response.getWriter().write(body);
    }

    // TODO: After setting https, should modify this method to set secure
    public static void addCookieWithHttpOnly(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
