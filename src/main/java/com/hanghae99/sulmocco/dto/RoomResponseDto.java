package com.hanghae99.sulmocco.dto;

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