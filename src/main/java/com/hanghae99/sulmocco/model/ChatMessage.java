package com.hanghae99.sulmocco.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class ChatMessage {


    public ChatMessage() {
    }

    @Builder
    public ChatMessage(MessageType type, String chatRoomId, String sender, String message, long userCount) {
        this.type = type;
        this.chatRoomId = chatRoomId;
        this.sender = sender;
        this.message = message;
        this.userCount = userCount;

    }



    // 메시지 타입 : 입장, 퇴장, 채팅
    public enum MessageType {
        ENTER, QUIT, TALK
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private MessageType type; // 메시지 타입

    @Column(nullable = false)
    private String chatRoomId; // 방번호

    @Column
    private String sender; // 메시지 보낸사람

    @Column
    private String message; // 메시지

    @Column
    private long userCount; // 채팅방 인원수, 채팅방 내에서 메시지가 전달될때 인원수 갱신시 사용


}