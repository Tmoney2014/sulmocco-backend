package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.Bookmark;
import com.hanghae99.sulmocco.model.Dish;
import com.hanghae99.sulmocco.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark , Long> {
    Optional<Bookmark> findByUserAndDish(User user, Dish dish);

    List<Bookmark> findByUser(User user);

    Slice<Bookmark> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
