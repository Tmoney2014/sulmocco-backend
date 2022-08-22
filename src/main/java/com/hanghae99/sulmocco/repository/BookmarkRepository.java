package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.Bookmark;
import com.hanghae99.sulmocco.model.Tables;
import com.hanghae99.sulmocco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark , Long> {
    Optional<Bookmark> findByUserAndTables(User user, Tables tables);

    List<Bookmark> findByUser(User user);
}
