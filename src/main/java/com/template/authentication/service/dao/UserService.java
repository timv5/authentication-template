package com.template.authentication.service.dao;

import com.template.authentication.model.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findUserById(Long userId);

    Optional<UserEntity> findUserByEmail(String email);

    void saveUser(final UserEntity user);

    boolean userExistsByEmail(String email);

}
