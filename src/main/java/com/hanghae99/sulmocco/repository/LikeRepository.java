package com.hanghae99.sulmocco.repository;


import com.hanghae99.sulmocco.model.Likes;
import com.hanghae99.sulmocco.model.Tables;
import com.hanghae99.sulmocco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {


    Optional<Likes> findByUserAndTables(User user, Tables tables);

    List<Likes> findByUserId(Long userId);
}


