package com.hanghae99.sulmocco.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TablesRequestDto {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 1, max = 50, message = "1 ~ 50글자 이내로 작성해주세요")
    private String title;

    @NotBlank(message = "닉네임을 입력해주세요")
    private String username;

    @NotBlank(message = "내용을 작성해주세요.")
    private String content;

    private String thumbnail;

    private List<String> imgUrlList;

    private String alcoholtag;

    private String freetag;
}
