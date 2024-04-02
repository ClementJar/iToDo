package com.jars.itodolist.repository;

import com.jars.itodolist.model.SecUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<SecUserEntity, Integer> {
    SecUserEntity findByUsernameIgnoreCase(String username);
    SecUserEntity findByEmailIgnoreCase(String email);
    boolean existsByEmail(String email);
}