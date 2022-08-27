package com.hanghae99.sulmocco.websocket;



import com.hanghae99.sulmocco.repository.EnterUserRepository;
import com.hanghae99.sulmocco.repository.RedisRepository;
import com.hanghae99.sulmocco.repository.RoomRepository;
import com.hanghae99.sulmocco.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final RedisRepository redisRepository;
    private final RoomRepository roomRepository;
    private final EnterUserRepository enterUserRepository;
    private final UserRepository userRepository;


    //     destination정보에서 roomId 추출
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1) {
            return destination.substring(lastIndex + 1);
        } else {
            return "";
        }
    }


    public void sendChatMessage(ChatMessage chatMessage) throws InterruptedException {

        chatMessage.setUserCount(redisRepository.getUserCount(chatMessage.getChatRoomId()));


        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            Thread.sleep(2000);
            chatMessage.setMessage(chatMessage.getSender() + "님이 방에 입장했습니다.");
            chatMessage.setSender("[알림]");

        }
        if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {

            chatMessage.setMessage(chatMessage.getSender() + "님이 방에서 나갔습니다.");
            chatMessage.setSender("[알림]");

        }
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }
}

