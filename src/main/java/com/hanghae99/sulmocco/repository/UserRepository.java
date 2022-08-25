package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(String Id);

    Optional<User> findByUsername(String userName);

    User findByUserId(Long userId);
}

//    User findByUsername(String sender);
//}
