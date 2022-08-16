package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.Tables;
import com.hanghae99.sulmocco.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TablesRepository extends JpaRepository<Tables, Long> {

    Slice<Tables> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);

    @Query("select t from Tables t ORDER BY t.likes.size DESC, t.id DESC ")
    List<Tables> findByOrderByLikesSize(Pageable pageable);
}
