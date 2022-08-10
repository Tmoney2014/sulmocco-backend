package com.hanghae99.sulmocco.dto;

import com.hanghae99.sulmocco.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    private String id;
    private String username;
    private String password;
    private String level;

    public User toEntity(SignUpRequestDto requestDto) {
        return User.builder()
                .id(requestDto.id)
                .username(requestDto.username)
                .password(requestDto.password)
                .level(requestDto.level)
                .build();
    }
}
