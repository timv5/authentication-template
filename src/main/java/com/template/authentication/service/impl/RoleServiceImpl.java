package com.template.authentication.service.impl;

import com.template.authentication.domain.impl.RolesJDBCDaoImpl;
import com.template.authentication.dto.response.ResponseCode;
import com.template.authentication.dto.response.RolesResponse;
import com.template.authentication.model.RoleEntity;
import com.template.authentication.service.dao.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RolesJDBCDaoImpl rolesJDBCDao;

    @Autowired
    public RoleServiceImpl(RolesJDBCDaoImpl rolesJDBCDao) {
        this.rolesJDBCDao = rolesJDBCDao;
    }

    @Override
    public RolesResponse getAllRoles() {
        List<RoleEntity> roleEntities = rolesJDBCDao.getAllRoles();
        return new RolesResponse(true, ResponseCode.Codes.SUCCESS.getCode(), ResponseCode.Codes.SUCCESS.name(), roleEntities);
    }
}
