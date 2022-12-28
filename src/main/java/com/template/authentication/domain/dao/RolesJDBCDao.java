package com.template.authentication.domain.dao;

import com.template.authentication.model.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface RolesJDBCDao {

    List<RoleEntity> getAllRoles();

}
