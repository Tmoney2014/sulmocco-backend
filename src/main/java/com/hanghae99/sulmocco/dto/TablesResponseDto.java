package com.hanghae99.sulmocco.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hanghae99.sulmocco.model.TableImage;
import com.hanghae99.sulmocco.model.Tables;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  //null인 데이터는 json 결과에 나오지 않음
public class TablesResponseDto {

    private String title;
    private String username;
    private String content;
    private List<String> imgUrlList;
    private String alcoholtag;
    private String freetag;
    private Integer likecount;
    private Integer viewcount;
    private LocalDateTime createdAt;
    private boolean isBookmark;
    private boolean isLike;
    private String profileimgurl;

    // 술상 추천 작성
    public TablesResponseDto(Tables tables) {
        this.title = tables.getTitle();
        this.username = tables.getTitle();
        this.content = tables.getContent();
        this.imgUrlList = tables.getImgUrls().stream()
                                            .map(this::apply)
                                            .collect(Collectors.toList());
        this.alcoholtag = tables.getAlcoholTag();
        this.freetag = tables.getFreeTag();
        this.createdAt = tables.getCreatedAt();
        this.profileimgurl = tables.getUser().getProfileUrl();
    }

    // 술상 추천 상세
    public TablesResponseDto(Tables tables, Boolean isBookmark, Boolean isLike) {
        this.title = tables.getTitle();
        this.username = tables.getTitle();
        this.content = tables.getContent();
        this.imgUrlList = tables.getImgUrls().stream()
                                            .map(this::apply)
                                            .collect(Collectors.toList());
        this.alcoholtag = tables.getAlcoholTag();
        this.freetag = tables.getFreeTag();
        this.likecount = tables.getLikes().size();
//        this.viewcount = ?
        this.isBookmark = isBookmark;
        this.isLike = isLike;
        this.createdAt = tables.getCreatedAt();
        this.profileimgurl = tables.getUser().getProfileUrl();
    }

    private String apply(TableImage tableImage) {
        return tableImage.getTableImgUrl();
    }
}
