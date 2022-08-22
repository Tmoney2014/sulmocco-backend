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
public class Tables extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tables_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 60000)
    private String content;

    @Column(nullable = false)
    private String alcoholTag;

    @Column(nullable = false)
    private String freeTag;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "tables", cascade = CascadeType.ALL)
    private List<Reply> replies;

    @JsonIgnore
    @OneToMany(mappedBy = "tables", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks;

    @JsonIgnore
    @OneToMany(mappedBy = "tables", cascade = CascadeType.ALL)
    private List<Likes> likes;

    @JsonIgnore
    @OneToMany(mappedBy = "tables", cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<TableImage> imgUrls = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "VIEWUSER", joinColumns = @JoinColumn(name = "tables_id"))
    List<Long> viewUserList = new ArrayList<>();

    @Column(nullable = false, length = 60000)
    private String thumbnailImgUrl;

    private int count;

    public void setUser(User user) {
        this.user = user;
    }

    public Tables(TablesRequestDto tablesRequestDto, User user) {
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
