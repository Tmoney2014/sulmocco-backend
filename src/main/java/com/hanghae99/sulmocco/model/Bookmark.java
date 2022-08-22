package com.hanghae99.sulmocco.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tables_id")
    private Tables tables;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Bookmark(User user, Tables tables) {
        this.user = user;
        this.tables = tables;
    }
}