package com.hanghae99.sulmocco.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@Component
public class HeaderTokenExtractor {

    /*
     * HEADER_PREFIX
     * header Authorization token 값의 표준이되는 변수
     */
    public final String HEADER_PREFIX = "Bearer ";

    public String extract(String header, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        /*
         * - Token 값이 올바르지 않은경우 -
         * header 값이 비어있거나 또는 HEADER_PREFIX 값보다 짧은 경우
         * 이셉션을(예외)를 던져주어야 합니다.
         */


        if (header == null || header.equals("") || header.length() < HEADER_PREFIX.length()) {
            System.out.println("error request : " + request.getRequestURI());

            ObjectMapper objectMapper = new ObjectMapper();

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(response.getWriter(),"올바른 JWT 값이 아닙니다.");
//            throw new NoSuchElementException("올바른 JWT 정보가 아닙니다.");

        }

        /*
         * - Token 값이 존재하는 경우 -
         * (bearer ) 부분만 제거 후 token 값 반환
         */
        return header.substring(
                HEADER_PREFIX.length(),
                header.length()
        );
    }
}
