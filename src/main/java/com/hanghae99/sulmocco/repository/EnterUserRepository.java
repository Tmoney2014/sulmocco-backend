package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.EnterUser;
import com.hanghae99.sulmocco.model.Room;
import com.hanghae99.sulmocco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnterUserRepository extends JpaRepository<EnterUser, Long> {
    EnterUser findByRoomAndUser(Room room, User user);

    List<EnterUser> findByRoom(Room room);
}
