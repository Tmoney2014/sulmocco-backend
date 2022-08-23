package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.Room;
import com.hanghae99.sulmocco.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {



    Room findByTitle(String title);

    Page<Room> findAllByOrderByCreatedAtDesc(Pageable pageable);

//
//    List<Room> findByUser(User user);
//
//    List<Room> findByNameContainingIgnoreCase(String keyword);

    Optional<Room> findByChatRoomId(String chatRoomId);
}