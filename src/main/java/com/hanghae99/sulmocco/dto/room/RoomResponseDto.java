package com.hanghae99.sulmocco.dto.room;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hanghae99.sulmocco.model.Room;
import lombok.*;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomResponseDto {

    private Long roodId;
    private String title;
    private String thumbnail;
    private String alcoholtag;
    private String food;
    private String theme;
    private String username;
    private Long userCount;
    private LocalDateTime createdAt;
    private Long roomId;
    private String chatRoomId;
    private String version;
    private String profileUrl;
    private boolean onair;

    private String level;

    public RoomResponseDto(Room room) {
        this.chatRoomId = room.getChatRoomId();
        this.version = room.getVersion();
        this.thumbnail = room.getThumbnail();
        this.title = room.getTitle();
        this.alcoholtag = room.getAlcoholTag();
        this.userCount = room.getUserCount();
        this.food = room.getFood();
        this.theme = room.getTheme();
        this.username = room.getUsername();
        this.profileUrl = room.getUser().getProfileUrl();
        this.createdAt = room.getCreatedAt();
        this.onair = room.isOnair();
        this.level = room.getUser().getLevel();
    }

    public static Slice<RoomResponseDto> roomList(Slice<Room> roomSlice) {
        Slice<RoomResponseDto> roomResponseDtos = roomSlice.map(r ->
                        RoomResponseDto.builder()
                                .chatRoomId(r.getChatRoomId())
                                .title(r.getTitle())
                                .thumbnail(r.getThumbnail())
                                .version(r.getVersion())
                                .userCount(r.getUserCount())
                                .alcoholtag(r.getAlcoholTag())
                                .food(r.getFood())
                                .theme(r.getTheme())
                                .username(r.getUser().getUsername())
                                .profileUrl(r.getUser().getProfileUrl())
                                .createdAt(r.getCreatedAt())
                                .onair(r.isOnair())
                                .level(r.getUser().getLevel())
                                .build()
        );
        return roomResponseDtos;
    }
}

