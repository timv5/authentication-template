package com.template.authentication.domain.mapper;

import com.template.authentication.model.RoleEntity;
import com.template.authentication.model.RoleName;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RolesMapper implements RowMapper<RoleEntity> {

    @Override
    public RoleEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleId(rs.getLong("role_id"));
        roleEntity.setRleName(RoleName.valueOf(rs.getString("role_name")));
        return roleEntity;
    }
}
