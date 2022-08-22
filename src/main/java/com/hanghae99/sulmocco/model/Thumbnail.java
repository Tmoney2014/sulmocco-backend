package com.hanghae99.sulmocco.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Entity
public class Thumbnail {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "tableImage_id")
    private Long id;

    @Column()
    private String thumbnailUrl;

    @JoinColumn(name = "tables_id", nullable = false)
    @OneToOne(fetch = LAZY)
    private Tables tables;

    public Thumbnail(String thumbnailUrl, Tables tables) {
        this.thumbnailUrl = thumbnailUrl;
        setTables(tables);
    }

    public void setTables(Tables tables) {
        this.tables = tables;
    }

    public void updateImgUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
