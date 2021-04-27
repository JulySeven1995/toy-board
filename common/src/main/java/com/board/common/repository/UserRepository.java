package com.board.common.repository;

import com.board.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    Boolean existsByUserId(String userId);

    void deleteByUserId(String userId);
}
