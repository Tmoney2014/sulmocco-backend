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

    @Column
    private String tableImgUrl;

    @JoinColumn
    @ManyToOne(fetch = LAZY)
    private Tables tables;

    public TableImage(String tableImgUrl, Tables tables) {
        this.tableImgUrl = tableImgUrl;
        setTableImage(tables);
    }

    //== 연관관계 (편의) 메서드==// 양방향 연관관계 세팅을 까먹지않고 할수있는 장점
    public void setTableImage(Tables tables) {
        this.tables = tables;
        tables.getImgUrls().add(this);
    }
}
