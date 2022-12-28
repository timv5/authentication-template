package com.template.authentication.dto.response;

import com.template.authentication.model.RoleEntity;

import java.util.HashSet;
import java.util.Set;

public class RegisterResponse extends BaseResponse {

    private Long userId;
    private String email;
    private Set<RoleEntity> roles = new HashSet<>();

    public RegisterResponse(boolean success, String code, String message) {
        super(success, code, message);
    }

    public RegisterResponse(boolean success, String code, String message, Long userId, String email, Set<RoleEntity> roles) {
        super(success, code, message);
        this.userId = userId;
        this.email = email;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
