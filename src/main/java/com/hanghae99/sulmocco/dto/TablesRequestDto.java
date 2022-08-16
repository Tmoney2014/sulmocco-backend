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

    @NotBlank
    private String title;

    @NotBlank
    private String username;

    @NotBlank
    private String content;

    private String thumbnail;

    private List<String> imgUrlList;

    @NotBlank
    private String alcoholtag;

    private String freetag;
}
