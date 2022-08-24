package com.hanghae99.sulmocco.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordChangeRequestDto {

    private String id;
    private String Password;
    private String Password2;
}
