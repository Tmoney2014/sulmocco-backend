package com.hanghae99.sulmocco.dto.bookmark;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.hanghae99.sulmocco.model.Bookmark;
import lombok.*;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
//앞단에 내가북마크한TABLES 표시하기위해 필요한 DTO
public class BookmarkResponseDto {
    private String title;
    private String username;
    private String content;
    private Long tableId;
    private int likecount;  //@OneToMany(mappedBy = "tables", cascade = CascadeType.ALL //private List<Like> likes; 사이즈로 갖고옴.
    private int viewcount;  //콜렉션으로 유저ID 저장 // 사이즈로 갖고옴.
    private String alcoholtag;
    private String freetag;
    private String profileimgurl; // 작성자 profileimg
    private String thumbnail;
    private LocalDateTime createdAt;

    public static Slice<BookmarkResponseDto> myPageBookmarkResponseDto(Slice<Bookmark> bookmarkSlice) {
            Slice<BookmarkResponseDto> bookmarkResponseDtoSlice = bookmarkSlice.map(t ->
                            BookmarkResponseDto.builder()
                                    .title(t.getDish().getTitle())
                                    .username(t.getDish().getUser().getUsername())
                                    .content(t.getDish().getContent())
                                    .tableId(t.getDish().getId())
                                    .likecount(t.getDish().getLikes().size())
                                    .viewcount(t.getDish().getViewUserList().size())
                                    .alcoholtag(t.getDish().getAlcoholTag())
                                    .freetag(t.getDish().getFreeTag())
                                    .profileimgurl(t.getDish().getUser().getProfileUrl())
                                    .thumbnail(t.getDish().getThumbnailImgUrl())
                                    .createdAt(t.getDish().getCreatedAt())
                                    .build()
            );
            return bookmarkResponseDtoSlice;
    }
}