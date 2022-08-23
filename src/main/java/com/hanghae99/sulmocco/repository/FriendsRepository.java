package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.Friends;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendsRepository extends JpaRepository<Friends, Long> {
    List<Friends> findAllByFriendsOrderByCreatedAtDesc(Friends findFriends);
}
