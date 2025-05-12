package com.codecool.backend.repository;

import com.codecool.backend.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    boolean deleteUserEntityById(int id);

    Optional<UserEntity> getUserEntityById(int id);

    Optional<UserEntity> getUserEntityByEmailAndPassword(String email, String password);

    Optional<UserEntity> findUserEntityByName(String username);

    Optional<UserEntity> findUserEntityByEmail(String email);
}
