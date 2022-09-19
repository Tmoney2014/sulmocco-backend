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
    @Query("select r from Room r where r.version in :setVersion ORDER BY r.userCount DESC, r.id DESC ")
    List<Room> findByOrderByCount(Pageable pageable, String[] setVersion);

    // 전체 목록
    @Query("select r from Room r where r.version in :setVersion")
    Page<Room> findAllRooms(Pageable pageable, String[] setVersion);

    // 술약속 검색
    @Query("select r from Room r where r.version in :setVersion and r.title LIKE %:keyword% Or r.alcoholTag LIKE %:keyword% Or r.food LIKE %:keyword% Or r.theme LIKE %:keyword% ")
    Page<Room> getRoomsBySearch(Pageable pageable, String keyword, String[] setVersion);

    // 술약속 검색 with version 설정
    @Query("select r from Room r where r.version=:version and r.title LIKE %:keyword% Or r.alcoholTag LIKE %:keyword% Or r.food LIKE %:keyword% Or r.theme LIKE %:keyword% ")
    Page<Room> getRoomsByVersionAndSearch(Pageable pageable, String keyword, String version);



    // 술 태그로만 조회
    @Query("select r from Room r where r.version in :setVersion and r.alcoholTag in :splitAlcoholTag ")
    Page<Room> getRoomsOrderByAlcoholTag(Pageable pageable, String[] splitAlcoholTag, String[] setVersion);

    // version으로만 조회
    @Query("select r from Room r where r.version=:version ")
    Page<Room> findAllRoomsByVersion(Pageable pageable, String version);

    // 술 version + 술태그 조회
    @Query("select r from Room r where r.version=:version and r.alcoholTag in :splitAlcoholTag ")
    Page<Room> getRoomsOrderByVersionAndAlcoholTag(Pageable pageable, String version, String[] splitAlcoholTag);

    Optional<Room> findByUsername(String username);
}
