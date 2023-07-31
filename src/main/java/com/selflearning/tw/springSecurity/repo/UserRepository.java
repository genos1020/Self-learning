package com.selflearning.tw.springSecurity.repo;

import com.selflearning.tw.springSecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserName(String username);
}
