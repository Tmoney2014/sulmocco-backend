package com.hanghae99.sulmocco.model;


import javax.persistence.*;

public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    @Column(name = "addFriend_id", nullable = false)
    private User user3;
}
//ToDo 유저가 유저를 북마크한경우 (?) 어떻게 할것인가 생각해보자


