package com.hanghae99.sulmocco.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae99.sulmocco.dto.TokenDto;
import com.hanghae99.sulmocco.model.RefreshToken;
import com.hanghae99.sulmocco.repository.RefreshTokenRepository;
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import com.hanghae99.sulmocco.security.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String AUTH_HEADER = "Authorization";
    public static final String REFRESH_TOKEN = "RefreshToken";
    public static final String TOKEN_TYPE = "BEARER";

    private ObjectMapper objectMapper = new ObjectMapper();
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {
        final UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        // Token 생성
//        final String token = JwtTokenUtils.generateJwtToken(userDetails);
        TokenDto tokenDto = JwtTokenUtils.generateJwtAndRefreshToken(userDetails.getUsername(), userDetails.getNickname());
        tokenDto.setLoginId(userDetails.getUser().getId());
        tokenDto.setNickname(userDetails.getNickname());

        response.addHeader(AUTH_HEADER, tokenDto.getAccessToken());
        response.addHeader(REFRESH_TOKEN, tokenDto.getRefreshToken());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByLoginId(userDetails.getUser().getId());

        if (!refreshToken.isPresent()) {
            // 처음 회원가입 / 로그인 시
            // refreshToken 저장
            RefreshToken refreshTokenEntity = RefreshToken.builder()
                                                          .loginId(userDetails.getUser().getId())
                                                          .value(tokenDto.getRefreshToken())
                                                          .build();
            refreshTokenRepository.save(refreshTokenEntity);
        } else {
            // 로그인을 다시 했는데, DB에 리프레시 토큰이 있으면 새로운 리프레시 토큰으로 재발행
            refreshToken.get().updateValue(tokenDto.getRefreshToken());
            refreshTokenRepository.save(new RefreshToken(userDetails.getUser().getId(), tokenDto.getRefreshToken()));
        }

        String id = userDetails.getUser().getId();
        String username = userDetails.getUser().getUsername();

        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream()
//                .write(objectMapper.writeValueAsBytes(new ResponseDto(true, id, username)));
                .write(objectMapper.writeValueAsBytes(tokenDto));
    }
}
