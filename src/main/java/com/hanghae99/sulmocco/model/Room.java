package com.hanghae99.sulmocco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae99.sulmocco.base.Timestamped;
import com.hanghae99.sulmocco.dto.RoomRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Room extends Timestamped implements Serializable {
    private static final long serialVersionUID = 6494678977089006639L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;
    private String chatRoomId;
    private String thumbnail;
    @Column(nullable = false)
    private String version;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String alcoholTag;
    @Column
    private Long userCount;
    @Column(nullable = false)
    private String food;
    @Column(nullable = false)
    private String theme;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private boolean onair;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<EnterUser> enterUsers = new ArrayList<>();

    public static Room create(RoomRequestDto requestDto, User user) {
        Room room = new Room();
        room.chatRoomId = UUID.randomUUID().toString();
        room.thumbnail = requestDto.getThumbnail();
        room.version = requestDto.getVersion();
        room.title = requestDto.getTitle();
        room.alcoholTag = requestDto.getAlcoholtag();
        room.userCount = 0L;
        room.user = user;
        room.food = requestDto.getFood();
        room.theme = requestDto.getTheme();
        room.username = user.getUsername();
        room.onair = true;
        return room;
    }
}



