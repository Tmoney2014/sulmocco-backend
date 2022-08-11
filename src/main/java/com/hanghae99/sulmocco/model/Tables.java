package com.hanghae99.sulmocco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae99.sulmocco.dto.TablesRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.hanghae99.sulmocco.base.Timestamped;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

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
    private String content;

    @Column(nullable = false)
    private String alcoholTag;

    @Column(nullable = false)
    private String freeTag;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "tables", cascade = CascadeType.ALL)
    private List<Reply> replies;

    @OneToMany(mappedBy = "tables", cascade = CascadeType.ALL)
    private List<Like> likes;

    @OneToMany(mappedBy = "tables", cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<TableImage> imgUrls = new ArrayList<>();


    public Tables(TablesRequestDto tablesRequestDto, User user) {
        this.title = tablesRequestDto.getTitle();
        this.content = tablesRequestDto.getContent();
        this.alcoholTag = tablesRequestDto.getAlcoholtag();
        this.freeTag = tablesRequestDto.getFreetag();
        this.imgUrls = tablesRequestDto.getImgUrlList().stream()
                    .map((tableImageUrl) -> new TableImage(tableImageUrl, this))
                    .collect(Collectors.toList());
        this.user = user;
    }

    public void update(TablesRequestDto tablesRequestDto) {
        this.title = tablesRequestDto.getTitle();
        this.content = tablesRequestDto.getContent();
        this.alcoholTag = tablesRequestDto.getAlcoholtag();
        this.freeTag = tablesRequestDto.getFreetag();
        this.imgUrls = tablesRequestDto.getImgUrlList().stream()
                .map((tableImageUrl) -> new TableImage(tableImageUrl, this))
                .collect(Collectors.toList());
    }
}
