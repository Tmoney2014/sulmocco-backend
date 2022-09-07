package com.hanghae99.sulmocco.dto.reply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyRequestDto {

    @NotBlank(message = "내용을 작성해주세요.")
    private String content;
}
