package com.hanghae99.sulmocco.model;

import com.hanghae99.sulmocco.base.Timestamped;
import com.hanghae99.sulmocco.dto.RoomRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Room extends Timestamped implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomId;

    @Column(nullable = false)
    private  String title;   // 방 이름
    @Column
    private String thumbnail;
    @Column
    private String version;
    @Column
    private String alcoholtag;
    @Column
    private String food;
    @Column
    private String theme;
    @Column
    private String roomUrl;
    @Column
    private Boolean isOpen;
    @Column
    private Long userCount;
    @Column
    private int count;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public static Room create(RoomRequestDto requestDto, User user) {
        Room room = new Room();
        room.roomId = UUID.randomUUID().toString();
        room.title = requestDto.getTitle();
        room.thumbnail = requestDto.getThumbnail();
        room.version = requestDto.getVersion();
        room.alcoholtag = requestDto.getAlcoholtag();
        room.food = requestDto.getFood();
        room.theme = requestDto.getTheme();
        room.roomUrl = requestDto.getRoomUrl();
        room.isOpen = requestDto.getIsOpen();
        room.userCount = 0L;
        room.count = requestDto.getCount();
        room.user = user;
        return room;
    }
}
