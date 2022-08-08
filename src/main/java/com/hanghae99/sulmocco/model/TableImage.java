package com.hanghae99.sulmocco.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @ManyToOne
    private Tables tables;

    public TableImage(String tableImg, Tables tables) {
        this.tableImgUrl = tableImg;
        this.tables = tables;
    }
}
