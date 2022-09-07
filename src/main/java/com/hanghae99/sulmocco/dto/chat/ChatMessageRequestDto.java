package com.hanghae99.sulmocco.dto.chat;


import com.hanghae99.sulmocco.model.ChatMessage;
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
