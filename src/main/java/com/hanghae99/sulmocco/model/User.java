package com.hanghae99.sulmocco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae99.sulmocco.base.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String level;

    @Column(nullable = false)
    private String profileUrl;

    @Column(nullable = false)
    private String role;

    // 여기서의 "user"는 Post에 있는 user필드에 의해서 매핑된 거울 역할
    // mappedBy 속성 : 나는 주인이 아니에요. 나는 연관관계의 거울이에요.(읽기 전용)
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Tables> tablesList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Reply> replies = new ArrayList<>();

//    @OneToMany(mappedBy = "user")
//    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarks = new ArrayList<>();

//    @OneToMany(mappedBy = "user")
//    private List<Friend> friends = new ArrayList<>();





}
