package com.template.authentication.controller;

import com.template.authentication.dto.request.ChangePasswordRequest;
import com.template.authentication.dto.response.BaseResponse;
import com.template.authentication.dto.response.RolesResponse;
import com.template.authentication.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/role")
@RestController
public class RolesController {

    private final RoleServiceImpl roleService;

    @Autowired
    public RolesController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<BaseResponse> getAllRoles() {
        RolesResponse response = this.roleService.getAllRoles();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
