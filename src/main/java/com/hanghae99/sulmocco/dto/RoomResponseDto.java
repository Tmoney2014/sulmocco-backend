package com.hanghae99.sulmocco.dto;

<<<<<<< HEAD
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
=======
import com.hanghae99.sulmocco.model.Room;
import com.hanghae99.sulmocco.model.Tables;
import com.hanghae99.sulmocco.repository.RedisRepository;
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Getter
@Setter
public class RoomResponseDto {

   private Long roomId;
   private String chatRoomId;
   private String version;
   private String tumbnail;
   private String title;
   private String alcoholtag;
   private Long userCount;
   private String food;
   private String theme;
   private String username;
   private String profileurl;
   private LocalDateTime creadtedAt;
   private boolean onair;

    public RoomResponseDto(Room room) {

        this.roomId =room.getId();
        this.chatRoomId = room.getChatRoomId();
        this.version = room.getVersion();
        this.tumbnail = room.getThumbnail();
        this.title = room.getTitle();
        this.alcoholtag = room.getAlcoholTag();
        this.userCount = room.getUserCount();
        this.food = room.getFood();
        this.theme = room.getTheme();
        this.username = room.getUsername();
        this.profileurl = room.getProfileimgurl();
        this.creadtedAt = room.getCreatedAt();
        this.onair = room.isOnair();
    }




}


//   “roomId”: “술약속 id”
//           “chatRoomId” : “채팅방 id”
//           “thumbnail”:”썸네일 이미지 url”,
//           “title” : “방제목”
//           “time”: “시간”,
//           “members” : “참여인원”,
//           “alcohol”:”주종”,
//           “food” : “안주”,
//           “theme”: “테마”,
//           “username : “사용자 이름”,
//           “onair” : “방송여부”
>>>>>>> e8c9964fa9c806ac52147a97dc59258c126246ed
