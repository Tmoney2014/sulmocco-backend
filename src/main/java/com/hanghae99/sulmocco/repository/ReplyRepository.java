package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.Reply;
import com.hanghae99.sulmocco.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByDishOrderByCreatedAtDesc(Dish findTable);
}
