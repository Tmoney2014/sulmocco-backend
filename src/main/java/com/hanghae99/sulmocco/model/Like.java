package com.hanghae99.sulmocco.model;

import com.fasterxml.jackson.annotation.JsonBackReference;


import javax.persistence.*;


public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKE_ID", nullable = false)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "TABLES_ID", nullable = false)
    private Tables tables;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public Like(User user, Tables tables) {
        this.user = user;
        this.tables = tables;
    }
}



