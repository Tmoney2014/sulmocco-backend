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
    @Column(name = "BOOKMARK_ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TABLES_ID", nullable = false)
    private Tables tables;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public Bookmark(User user, Tables tables) {
        this.user = user;
        this.tables = tables;
    }
}