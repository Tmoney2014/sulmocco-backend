package com.hanghae99.sulmocco.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) //null 값은 결과에 나오지 않음.
public class ChangeRequestDto {

    private Long userId;
    private String id;
    private String password;
    private String password2;
    private String username;
    private String profileUrl;
    private String level;

}
