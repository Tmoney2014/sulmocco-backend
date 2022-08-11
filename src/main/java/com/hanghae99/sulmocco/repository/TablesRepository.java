package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.Tables;
import com.hanghae99.sulmocco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TablesRepository extends JpaRepository<Tables, Long> {

    Tables findByUser(User user);
}
