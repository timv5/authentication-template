package com.template.authentication.dto.response;

import com.template.authentication.model.RoleEntity;

import java.util.List;

public class RolesResponse extends BaseResponse {

    private List<RoleEntity> roles;

    public RolesResponse(boolean success, String code, String message) {
        super(success, code, message);
    }

    public RolesResponse(boolean success, String code, String message, List<RoleEntity> roles) {
        super(success, code, message);
        this.roles = roles;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "RolesResponse{" +
                "roles=" + roles +
                '}';
    }
}
