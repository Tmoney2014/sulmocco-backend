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
    @JoinColumn(name = "tables_id", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Tables tables;

    public TableImage(String tableImgUrl, Tables tables) {
        this.tableImgUrl = tableImgUrl;
        setTables(tables);
    }
    //== 연관관계 메서드 ==//
    public void setTables(Tables tables) {
        this.tables = tables;
        tables.getImgUrls().add(this);
    }

    public void update(String tableImgUrl) {
        this.tableImgUrl = tableImgUrl;
    }
}
