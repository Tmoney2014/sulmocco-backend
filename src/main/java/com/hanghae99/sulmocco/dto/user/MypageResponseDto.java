package com.hanghae99.sulmocco.dto.user;

import com.hanghae99.sulmocco.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MypageResponseDto {

    private String username;
    private String level;
    private String id;
    private String profileUrl;

    public MypageResponseDto (User user) {
        this.id = user.getId();
        this.level = user.getLevel();
        this.profileUrl = user.getProfileUrl();
        this.username = user.getUsername();
    }
}
