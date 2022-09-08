package com.hanghae99.sulmocco.dto.tables;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hanghae99.sulmocco.dto.reply.ReplyResponseDto;
import com.hanghae99.sulmocco.model.Dish;
import com.hanghae99.sulmocco.model.TableImage;
import lombok.*;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)  //null인 데이터는 json 결과에 나오지 않음
public class TablesResponseDto {

    private Long tableId;
    private String title;
    private String username;
    private String content;
    private String thumbnail;
    private List<String> imgUrlList;
    private String alcoholtag;
    private String freetag;
    private Integer likecount;
    private Integer viewcount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private boolean isBookmark;
    private boolean isLike;
    private String profileimgurl;
    private String level;

    private String loginId;

    private List<ReplyResponseDto> replies;

    // 술상 추천 목록, 본인이 작성한 술상 목록
    public TablesResponseDto(Dish dish) {
        this.tableId = dish.getId();
        this.title = dish.getTitle();
        this.username = dish.getUser().getUsername();
        this.thumbnail = dish.getThumbnailImgUrl();
        this.likecount = dish.getLikes().size();
        this.viewcount = dish.getViewUserList().size();
        this.alcoholtag = dish.getAlcoholTag();
        this.freetag = dish.getFreeTag();
        this.createdAt = dish.getCreatedAt();
        this.level = dish.getUser().getLevel();
        this.profileimgurl = dish.getUser().getProfileUrl();
    }

    // 술상 추천 상세
    public TablesResponseDto(Dish dish, List<ReplyResponseDto> replies,
                             Boolean isBookmark, Boolean isLike) {
        this.tableId = dish.getId();
        this.title = dish.getTitle();
        this.username = dish.getUser().getUsername();
        this.content = dish.getContent();
        this.thumbnail = dish.getThumbnailImgUrl();
        this.imgUrlList = dish.getImgUrls().stream()
                .map(this::apply)
                .collect(Collectors.toList());
        this.replies = replies; // findTable.getReplies(); // 이걸로도 되나 궁금하다
        this.alcoholtag = dish.getAlcoholTag();
        this.freetag = dish.getFreeTag();
        this.likecount = dish.getLikes().size();
        this.viewcount = dish.getViewUserList().size();
        this.isBookmark = isBookmark;
        this.isLike = isLike;
        this.createdAt = dish.getCreatedAt();
        this.profileimgurl = dish.getUser().getProfileUrl();
        this.modifiedAt = dish.getModifiedAt();
        this.level = dish.getUser().getLevel();
        this.loginId = dish.getUser().getId();
    }

    private String apply(TableImage tableImage) {
        return tableImage.getTableImgUrl();
    }

    // 오늘의 술상 추천
    public static TablesResponseDto todayTableDto(Dish dish) {
        return TablesResponseDto.builder()
                .tableId(dish.getId())
                .thumbnail(dish.getThumbnailImgUrl())
                .title(dish.getTitle())
                .likecount(dish.getLikes().size())
                .viewcount(dish.getViewUserList().size())
                .alcoholtag(dish.getAlcoholTag())
                .freetag(dish.getFreeTag())
                .profileimgurl(dish.getUser().getProfileUrl())
                .username(dish.getUser().getUsername())
                .level(dish.getUser().getLevel())
                .createdAt(dish.getCreatedAt())
                .build();
    }

    // 본인이 작성한 술상 목록
    public static Slice<TablesResponseDto> myPageTablesResponseDto(Slice<Dish> tableSlice) {
        Slice<TablesResponseDto> tablesResponseDtoSlice = tableSlice.map(t ->
                        TablesResponseDto.builder()
                                .tableId(t.getId())
                                .username(t.getUser().getUsername())
                                .title(t.getTitle())
                                .content(t.getContent())
                                .thumbnail(t.getThumbnailImgUrl())
                                .likecount(t.getLikes().size())
                                .viewcount(t.getViewUserList().size())
                                .alcoholtag(t.getAlcoholTag())
                                .freetag(t.getFreeTag())
                                .createdAt(t.getCreatedAt())
                                .profileimgurl(t.getUser().getProfileUrl())
                                .level(t.getUser().getLevel())
                                .build()
        );
        return tablesResponseDtoSlice;
    }
}
