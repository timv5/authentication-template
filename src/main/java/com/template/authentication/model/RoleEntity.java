package com.template.authentication.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", length = 60)
    private RoleName rleName;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public RoleName getRleName() {
        return rleName;
    }

    public void setRleName(RoleName rleName) {
        this.rleName = rleName;
    }

    public RoleEntity() {
    }

    public RoleEntity(Long roleId, RoleName rleName) {
        this.roleId = roleId;
        this.rleName = rleName;
    }
}
