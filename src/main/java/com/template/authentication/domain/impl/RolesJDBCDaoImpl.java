package com.template.authentication.domain.impl;

import com.template.authentication.domain.dao.RolesJDBCDao;
import com.template.authentication.domain.mapper.RolesMapper;
import com.template.authentication.model.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RolesJDBCDaoImpl implements RolesJDBCDao {

    private static final RolesMapper ROLES_MAPPER = new RolesMapper();
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RolesJDBCDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<RoleEntity> getAllRoles() {
        return jdbcTemplate.query(SQLConstants.GET_ALL_ROLES, ROLES_MAPPER);
    }

    private static class SQLConstants {

        private static final String GET_ALL_ROLES = " select r.role_id, " +
                                                    "       r.role_name " +
                                                    " from roles r ";

    }
}
