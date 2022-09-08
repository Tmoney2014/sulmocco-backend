package com.hanghae99.sulmocco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae99.sulmocco.dto.tables.TablesRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.hanghae99.sulmocco.base.Timestamped;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class Dish extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 60000, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String alcoholTag;

    @Column(nullable = true , length = 10)
    private String freeTag;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();;

    @JsonIgnore
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks = new ArrayList<>();;

    @JsonIgnore
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();;

    @JsonIgnore
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    private List<TableImage> imgUrls = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "VIEWUSER", joinColumns = @JoinColumn(name = "dish_id"))
    List<Long> viewUserList = new ArrayList<>();

    @Column()
    private String thumbnailImgUrl;

    private int count;

    public Dish(String title, String content, String alcoholTag, String freetag, String thumbnailImgUrl, User user) {
        this.title = title;
        this.content = content;
        this.alcoholTag = alcoholTag;
        this.freeTag = freetag;
        this.thumbnailImgUrl = thumbnailImgUrl;
        setUser(user);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dish(TablesRequestDto tablesRequestDto, User user) {
        this.title = tablesRequestDto.getTitle();
        this.content = tablesRequestDto.getContent();
        this.alcoholTag = tablesRequestDto.getAlcoholtag();
        this.freeTag = tablesRequestDto.getFreetag();
        this.thumbnailImgUrl = tablesRequestDto.getThumbnail();
//        this.imgUrls = tablesRequestDto.getImgUrlList().stream()
//                    .map((tableImageUrl) -> new TableImage(tableImageUrl, this))
//                    .collect(Collectors.toList());
        this.count = 0;
        setUser(user);
    }

    public void update(TablesRequestDto tablesRequestDto) {
        this.title = tablesRequestDto.getTitle();
        this.content = tablesRequestDto.getContent();
        this.alcoholTag = tablesRequestDto.getAlcoholtag();
        this.freeTag = tablesRequestDto.getFreetag();
        this.thumbnailImgUrl = tablesRequestDto.getThumbnail();
//        this.imgUrls = tablesRequestDto.getImgUrlList().stream()
//                .map((tableImageUrl) -> new TableImage(tableImageUrl, this))
//                .collect(Collectors.toList());
    }

    public void addViewUser(Long userId) {
        if (this.viewUserList.contains(userId)) {
            return;
        }
        viewUserList.add(userId);
        count = viewUserList.size();
    }

    public void addTableImage(TableImage tableImage) {
        this.getImgUrls().add(tableImage);
    }
}
