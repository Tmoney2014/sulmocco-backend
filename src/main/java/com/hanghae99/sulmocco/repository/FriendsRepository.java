package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.Friends;
import com.hanghae99.sulmocco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FriendsRepository extends JpaRepository<Friends, Long> {

//    List<Friends> findAllByFriendsOrderByCreatedAtDesc(Friends findFriends);

    @Query("select f.addFriendId from Friends f where f.user =: user")
    List<Long> findByUser(@Param("user") User user);

//    Friends findByUserIdAndUser(Long userId, User user);
    Friends findByAddFriendIdAndUser(Long userId, User user);
}
