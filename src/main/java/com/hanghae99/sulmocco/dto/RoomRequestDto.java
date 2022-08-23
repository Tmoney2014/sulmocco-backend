package com.hanghae99.sulmocco.dto;

<<<<<<< HEAD
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
=======

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoomRequestDto {

    String thumbnail;

    String title;

    String version;

    String alcoholtag;

    String food;

    String theme;

}

>>>>>>> e8c9964fa9c806ac52147a97dc59258c126246ed
