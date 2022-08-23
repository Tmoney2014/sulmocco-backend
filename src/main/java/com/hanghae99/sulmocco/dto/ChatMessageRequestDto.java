package com.hanghae99.sulmocco.dto;


import com.hanghae99.sulmocco.websocket.ChatMessage;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequestDto {
    private ChatMessage.MessageType type;

    //TODO 룸아이디 변경

    private Long roomId;
    private String sender;
    private String message;
    private long userCount;
    private String profileImg;
}
