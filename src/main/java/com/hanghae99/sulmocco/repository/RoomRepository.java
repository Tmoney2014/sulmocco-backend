package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByTitle(String title);

    Optional<Room> findByChatRoomId(String chatRoomId);

    // 지금 인기있는 술약속 Top 8
    @Query("select r from Room r ORDER BY r.userCount DESC, r.id DESC ")
    List<Room> findByOrderByCount(Pageable pageable);

    // 전체 목록
    @Query("select r from Room r ")
//    Slice<Room> findAllRooms(Pageable pageable);
    Page<Room> findAllRooms(Pageable pageable);

    // 전체 술모임 검색
    @Query("select r from Room r where r.title LIKE %:keyword% Or r.alcoholTag LIKE %:keyword% Or r.food LIKE %:keyword% Or r.theme LIKE %:keyword% ")
//    Slice<Room> getRoomsBySearch(Pageable pageable, @Param("keyword") String keyword);
    Page<Room> getRoomsBySearch(Pageable pageable, @Param("keyword") String keyword);

    // 술 태그로 조회 (다중태그)
    @Query("select r from Room r where r.alcoholTag in :splitAlcoholTag ")
//    Slice<Room> getRoomsOrderByAlcoholTag(Pageable pageable, String[] splitAlcoholTag);
    Page<Room> getRoomsOrderByAlcoholTag(Pageable pageable, String[] splitAlcoholTag);

}
