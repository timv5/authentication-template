package com.template.authentication.config;

import com.template.authentication.model.UserEntity;
import com.template.authentication.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserServiceImpl userService;

    @Autowired
    public CustomUserDetailsService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Transactional
    public UserDetails loadUserByUserId(Long userId) throws UsernameNotFoundException {
        Optional<UserEntity> user = userService.findUserById(userId);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with id " + userId + " not found");
        }

        return UserPrincipal.build(user.get());
    }

    /**
     * Username represents email
     * @param username email
     * @throws UsernameNotFoundException
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userService.findUserByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        return UserPrincipal.build(user.get());
    }

}
