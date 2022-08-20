package com.hanghae99.sulmocco.model;

import com.hanghae99.sulmocco.base.Timestamped;
import com.hanghae99.sulmocco.dto.RoomRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class Room extends Timestamped implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;


    private String chatRoomId;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private String version;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String alcoholTag;

    @Column(nullable = false)
    private String food;

    @Column(nullable = false)
    private String theme;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private boolean onair;

//    @Column(nullable = false)
    private String profileimgurl;


    public static Room create(RoomRequestDto requestDto, String username, String userprofileurl) {
        Room room = new Room();
        room.chatRoomId = UUID.randomUUID().toString();
        room.thumbnail = requestDto.getThumbnail();
        room.version = requestDto.getVersion();
        room.title = requestDto.getTitle();
        room.alcoholTag = requestDto.getAlcoholtag();
        room.food = requestDto.getFood();
        room.theme = requestDto.getTheme();
        room.username = username;
        room.onair = true;
        room.profileimgurl = userprofileurl;
        return room;
    }
}



