package com.hanghae99.sulmocco.model;

import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKE_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
//    @JsonBackReference
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Likes(User user, Dish dish) {
        this.user = user;
        this.dish = dish;
    }
}



