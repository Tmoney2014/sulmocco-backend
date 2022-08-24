package com.hanghae99.sulmocco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae99.sulmocco.base.Timestamped;
import com.hanghae99.sulmocco.dto.PasswordChangeRequestDto;
import com.hanghae99.sulmocco.dto.SignUpRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sun.security.util.Password;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String id;  // 로그인 아이디

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;    // 우리 서비스에서는 닉네임으로 사용된다.

    @Column(nullable = false)
    private String level;

    private String profileUrl;

//    @OneToMany(mappedBy = "user")
//    private List<Bookmark> bookmarks = new ArrayList<>();

//    @OneToMany(mappedBy = "user")
//    private List<Friend> friends = new ArrayList<>();


    @Builder
    public User(String id, String password, String username, String level, String profileUrl) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.level = level;
        this.profileUrl = profileUrl;
    }

    public User(SignUpRequestDto signUpRequestDto, String password) {
        this.id = signUpRequestDto.getId();
        this.password = password;
        this.username = signUpRequestDto.getUsername();
        this.level = signUpRequestDto.getLevel();
//        this.profileUrl = signUpRequestDto.profileUrl;
//        this.role = role;
    }

    public void update(String password, User user) {
        this.id = user.getId();
        this.password = password;
        this.level = user.getLevel();
        this.username = user.getUsername();
        this.profileUrl = user.getProfileUrl();
        this.userId = user.getUserId();

    }
}
