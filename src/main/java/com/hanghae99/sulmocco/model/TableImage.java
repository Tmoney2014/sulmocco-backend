package com.hanghae99.sulmocco.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class TableImage {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "tableImage_id")
    private Long id;

    @Column()
    private String tableImgUrl;
    @JoinColumn(name = "dish_id", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Dish dish;

    public TableImage(String tableImgUrl, Dish dish) {
        this.tableImgUrl = tableImgUrl;
        setDish(dish);
    }
    //== 연관관계 메서드 ==//
    public void setDish(Dish dish) {
        this.dish = dish;
        dish.getImgUrls().add(this);
    }

    public void update(String tableImgUrl) {
        this.tableImgUrl = tableImgUrl;
    }
}
