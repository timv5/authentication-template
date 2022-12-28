package com.template.authentication.domain;

import com.template.authentication.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersDao extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUsersByUserId(Long userId);

    Optional<UserEntity> findUsersByEmail(String email);

    boolean existsUsersByEmail(String email);

}
