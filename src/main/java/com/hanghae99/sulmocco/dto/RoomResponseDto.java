package com.hanghae99.sulmocco.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hanghae99.sulmocco.model.Room;
import lombok.*;
import org.springframework.data.domain.Slice;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  //null인 데이터는 json 결과에 나오지 않음
public class RoomResponseDto {

    private Long roodId;
    private String title;
    private String thumbnail;
    private String alcoholtag;
    private String food;
    private String theme;
    private String username;
//    private String airTime; 방송 시간인가 ?
    private int count;
    private Long userCount;

    public RoomResponseDto(Room room) {
        this.title = room.getTitle();
        this.thumbnail = room.getThumbnail();
        this.theme = room.getTheme();
        this.alcoholtag = room.getAlcoholtag();
        this.food = room.getFood();
        this.username = room.getUser().getUsername();
        this.count = room.getCount();
        this.userCount = room.getUserCount();
    }

    public static Slice<RoomResponseDto> roomList(Slice<Room> roomSlice) {
        Slice<RoomResponseDto> roomResponseDtos = roomSlice.map(r ->
                        RoomResponseDto.builder()
                                .title(r.getTitle())
                                .thumbnail(r.getThumbnail())
//                                .members
                                .roodId(r.getId())
                                .alcoholtag(r.getAlcoholtag())
                                .food(r.getFood())
                                .theme(r.getTheme())
                                .username(r.getUser().getUsername())
//                                .onair
                                .build()
        );
        return roomResponseDtos;
    }
}
