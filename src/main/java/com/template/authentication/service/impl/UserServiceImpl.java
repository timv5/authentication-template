package com.template.authentication.service.impl;

import com.template.authentication.domain.UsersDao;
import com.template.authentication.model.UserEntity;
import com.template.authentication.service.dao.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UsersDao userDao;

    @Autowired
    public UserServiceImpl(UsersDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<UserEntity> findUserById(final Long userId) {
        return this.userDao.findUsersByUserId(userId);
    }

    @Override
    public Optional<UserEntity> findUserByEmail(final String email) {
        return this.userDao.findUsersByEmail(email);
    }

    @Transactional
    @Override
    public void saveUser(UserEntity user) {
        userDao.save(user);
    }

    @Override
    public boolean userExistsByEmail(String email) {
        return userDao.existsUsersByEmail(email);
    }
}
