package com.hanghae99.sulmocco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.hanghae99.sulmocco.base.Timestamped;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Tables extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tables_id")
    private  Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String alcoholTag;

    @Column(nullable = false)
    private String freeTag;

    @JsonIgnore
    @OneToMany(mappedBy = "tables", cascade = CascadeType.ALL)
    private List<Reply> replies;

//    @OneToMany(mappedBy = "tables", cascade = CascadeType.ALL)
//    private List<Like> likes;

    @OneToMany(mappedBy = "tables", cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<TableImage> imgUrls = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
