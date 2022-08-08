package com.hanghae99.sulmocco.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class UserImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userImage_id")
    private Long id;
    @Column
    private String userImgUrl;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    public UserImage(String userImgUrl, User user) {
        this.userImgUrl = userImgUrl;
        this.user = user;
    }
}
