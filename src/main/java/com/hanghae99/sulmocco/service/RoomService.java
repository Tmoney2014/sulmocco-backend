package com.hanghae99.sulmocco.service;

import com.hanghae99.sulmocco.dto.RoomRequestDto;
import com.hanghae99.sulmocco.model.Room;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.repository.RoomRepository;
import com.hanghae99.sulmocco.websocket.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    // 채팅방(topic)에 발행되는 메시지를 처리할 Listner
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    // Redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Room> opsHashChatRoom;
    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
    private Map<String, ChannelTopic> topics;

    private final RoomRepository roomRepository;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }


    public List<Room> findAllRoom() {
        return opsHashChatRoom.values(CHAT_ROOMS);
    }

    public Room findRoomById(String id) {
        return opsHashChatRoom.get(CHAT_ROOMS, id);
    }
    /**
     * 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
     */
    public ResponseEntity<?> createRoom(RoomRequestDto requestDto, String username, String userprofileurl) {
        Room room = Room.create(requestDto, username, userprofileurl);
        roomRepository.save(room);
        opsHashChatRoom.put(CHAT_ROOMS, room.getChatRoomId(), room);
        return new ResponseEntity<>("방생성완료", HttpStatus.valueOf(200));
    }

    /**
     * 채팅방 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다.
     */
    public void enterChatRoom(String chatRoomId) {
        ChannelTopic topic = topics.get(chatRoomId);
        if (topic == null)
            topic = new ChannelTopic(chatRoomId);
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        topics.put(chatRoomId, topic);
    }

    public ChannelTopic getTopic(String chatRoomId) {
        return topics.get(chatRoomId);
    }
}



