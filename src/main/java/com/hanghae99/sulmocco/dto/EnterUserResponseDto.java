package com.hanghae99.sulmocco.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnterUserResponseDto {
    private String username;
    private String profileurl;

    public EnterUserResponseDto(String username, String profileurl) {
        this.username = username;
        this.profileurl = profileurl;
    }
}
