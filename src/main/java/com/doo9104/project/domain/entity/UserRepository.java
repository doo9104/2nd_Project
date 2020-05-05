package com.doo9104.project.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserid(String userid);
}
