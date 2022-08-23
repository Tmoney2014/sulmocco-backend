package com.hanghae99.sulmocco.repository;


import com.hanghae99.sulmocco.model.EnterUser;
import com.hanghae99.sulmocco.model.Room;
import com.hanghae99.sulmocco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnterUserRepository extends JpaRepository<EnterUser, Long > {
    List<EnterUser> findByRoom(Room room);

//    EnterUser findByRoomAndUser(Optional<Room> room, User user);

    EnterUser deleteByRoomAndUser(Room room, User user);

    EnterUser findByRoomAndUser(Room room, User user);
}
