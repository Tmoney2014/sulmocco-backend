package com.hanghae99.sulmocco.model;

import com.hanghae99.sulmocco.base.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Reply extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="dish_id")
    private Dish dish;

    //== 연관관계 (편의) 메서드==//
    public void setUser(User user) {
        this.user = user;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Reply(String content, User user, Dish dish) {
        this.content = content;
        setUser(user);
        setDish(dish);
    }

    public void update(String content, User user) {
        this.content = content;
        this.user = user;
    }

}
