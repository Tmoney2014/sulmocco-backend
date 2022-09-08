package com.hanghae99.sulmocco.model;

import com.hanghae99.sulmocco.dto.user.ChangeRequestDto;
import com.hanghae99.sulmocco.dto.user.SignUpRequestDto;
import com.hanghae99.sulmocco.dto.user.UpdateUserRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Friends> friends = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Dish> dishes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<EnterUser> enterUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

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

    public void updatePw(String password, User user) {
        this.id = user.getId();
        this.password = password;
        this.level = user.getLevel();
        this.username = user.getUsername();
        this.profileUrl = user.getProfileUrl();
        this.userId = user.getUserId();

    }

    public void updateUser(UpdateUserRequestDto updateUserRequestDto) {
        this.username = updateUserRequestDto.getUsername();
        this.level = updateUserRequestDto.getLevel();
        this.profileUrl = updateUserRequestDto.getProfileUrl();
    }
}
