package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(String username);
}
