package com.example.kanbansystem.Repository;

import com.example.kanbansystem.entities.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<user,Long> {
    Optional<user> findByUsername(String username);


}
