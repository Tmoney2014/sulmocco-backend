package com.hanghae99.sulmocco.dto.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponseDto {

    private String url;

    private String filename;

}
