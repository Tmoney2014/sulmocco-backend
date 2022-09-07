package com.hanghae99.sulmocco.dto.token;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    private boolean status;
    private String message;

    private String loginId;

    private String nickname;

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpiresIn;

    public TokenDto(boolean status, String message){
        this.status = status;
        this.message = message;
    }

}
