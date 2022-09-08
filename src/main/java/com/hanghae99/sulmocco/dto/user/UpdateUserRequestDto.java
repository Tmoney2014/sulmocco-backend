package com.hanghae99.sulmocco.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDto {

    private String username;
    private String profileUrl;
    private String level;

}
