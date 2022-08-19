package com.hanghae99.sulmocco.dto;

import com.hanghae99.sulmocco.model.TableImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TablesRequestDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "username이 없습니다")
    private String username;

    @NotBlank(message = "내용을 작성해주세요.")
    private String content;

    private String thumbnail;

    private List<String> imgUrlList;

    private String alcoholtag;

    private String freetag;
}
