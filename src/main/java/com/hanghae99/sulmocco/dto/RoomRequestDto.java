package com.hanghae99.sulmocco.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomRequestDto {

    private String version;
    private String title;
    private String thumbnail;
    private String alcoholtag;
    private String food;
    private String theme;
    private String roomUrl;
    private Boolean isOpen;
    private int count;
    private Long userCount;

}


