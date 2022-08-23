package com.hanghae99.sulmocco.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnterUserResponseDto {
    private String nickname;
    private String profileImg;

    public EnterUserResponseDto(String nickname, String profileImg) {
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}
