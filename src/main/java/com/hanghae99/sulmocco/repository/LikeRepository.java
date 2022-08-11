package com.hanghae99.sulmocco.repository;


import com.hanghae99.sulmocco.model.Like;
import com.hanghae99.sulmocco.model.Tables;
import com.hanghae99.sulmocco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {


    Optional<Like> findByUserAndTables(User user, Tables tables);

    List<Like> findByUserId(Long userId);
}


