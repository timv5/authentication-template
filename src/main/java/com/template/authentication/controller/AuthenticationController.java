package com.template.authentication.controller;

import com.template.authentication.config.constraints.ActiveUser;
import com.template.authentication.dto.request.ChangePasswordRequest;
import com.template.authentication.dto.request.LoginRequest;
import com.template.authentication.dto.request.RegisterRequest;
import com.template.authentication.dto.response.BaseResponse;
import com.template.authentication.dto.response.LoginResponse;
import com.template.authentication.dto.response.RegisterResponse;
import com.template.authentication.dto.response.UserDetailsResponse;
import com.template.authentication.service.impl.AuthenticationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/user")
@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final AuthenticationServiceImpl authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/change/password")
    public ResponseEntity<BaseResponse> changePassword(@RequestBody final ChangePasswordRequest request) {
        BaseResponse response = this.authenticationService.changePassword(request);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @ActiveUser
    @GetMapping("/details")
    public ResponseEntity<UserDetailsResponse> fetchUserDetails() {
        UserDetailsResponse response = this.authenticationService.fetchUserDetails();
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody final LoginRequest request) {
        LOGGER.info("Incoming request: {}", request.toString());
        LoginResponse response = this.authenticationService.login(request);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/auth/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody final RegisterRequest request) {
        LOGGER.info("Incoming request: {}", request.toString());
        RegisterResponse response = this.authenticationService.register(request);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
