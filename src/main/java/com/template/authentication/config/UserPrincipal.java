package com.template.authentication.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.template.authentication.model.UserEntity;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private final Long userId;
    private final String email;
    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private boolean isActive;

    public UserPrincipal(Long userId, String email, String password, Collection<? extends GrantedAuthority> authorities, boolean isActive) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    public static UserPrincipal build(final UserEntity user) {
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRleName().name())).collect(Collectors.toList());
        return new UserPrincipal(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                BooleanUtils.toBoolean(user.getActive())
        );
    }


    public boolean isActive() {
        return isActive;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (Objects.isNull(o)|| getClass() != o.getClass()) return false;

        UserPrincipal user = (UserPrincipal) o;
        return Objects.equals(userId, user.userId);
    }
}
