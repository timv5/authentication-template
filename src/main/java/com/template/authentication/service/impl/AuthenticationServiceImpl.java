package com.template.authentication.service.impl;

import com.template.authentication.config.AppProperties;
import com.template.authentication.config.TokenProvider;
import com.template.authentication.config.UserPrincipal;
import com.template.authentication.dto.request.ChangePasswordRequest;
import com.template.authentication.dto.response.*;
import com.template.authentication.exception.AuthenticationException;
import com.template.authentication.model.RoleEntity;
import com.template.authentication.model.RoleName;
import com.template.authentication.model.UserEntity;
import com.template.authentication.dto.request.LoginRequest;
import com.template.authentication.dto.request.RegisterRequest;
import com.template.authentication.service.dao.AuthenticationService;
import com.template.authentication.util.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userService;
    private final TokenProvider tokenProvider;
    private final AppProperties appProperties;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                                     UserServiceImpl userService, TokenProvider tokenProvider, AppProperties appProperties) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.appProperties = appProperties;
    }

    public LoginResponse login(LoginRequest request) {
        try {
            BaseResponse validation = authenticationRequestValidation(request);
            if (!validation.isSuccess()) {
                return new LoginResponse(false, validation.getCode(), validation.getMessage());
            }

            Optional<UserEntity> user = userService.findUserByEmail(request.getEmail());
            if (user.isEmpty()) {
                LOGGER.error("User {} doesn't exists", request.getEmail());
                return new LoginResponse(false, ResponseCode.Codes.AUTHENTICATION_ERROR.getCode(), ResponseCode.Codes.AUTHENTICATION_ERROR.name());
            }

            if (!this.passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
                LOGGER.error("Password miss match for user {}", user.get().getUserId());
                return new LoginResponse(false, ResponseCode.Codes.AUTHENTICATION_ERROR.getCode(), ResponseCode.Codes.AUTHENTICATION_ERROR.name());
            }

            Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = this.tokenProvider.generateJwtToken(authentication);
            Long expirationTime = this.appProperties.getAuth().getExpirationTime();

            return new LoginResponse(true, ResponseCode.Codes.SUCCESS.getCode(), ResponseCode.Codes.SUCCESS.name(),
                    user.get().getUserId(), jwt, expirationTime);
        } catch (Exception e) {
            return new LoginResponse(true, ResponseCode.Codes.EXCEPTION.getCode(), ResponseCode.Codes.EXCEPTION.name());
        }
    }

    public RegisterResponse register(RegisterRequest request) {
        try {
            BaseResponse validation = authenticationRequestValidation(request);
            if (!validation.isSuccess()) {
                return new RegisterResponse(false, validation.getCode(), validation.getMessage());
            }

            boolean userExists = userService.userExistsByEmail(request.getEmail());
            if (userExists) {
                LOGGER.error("User already {} exists", request.getEmail());
                return new RegisterResponse(false, ResponseCode.Codes.AUTHENTICATION_ERROR.getCode(), ResponseCode.Codes.AUTHENTICATION_ERROR.name());
            }

            Set<RoleEntity> roles = new HashSet<>();
            roles.add(new RoleEntity(1L, RoleName.ROLE_USER));
            UserEntity user = UserEntity.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .isActive(true)
                    .timeUpdated(LocalDateTime.now())
                    .timeCreated(LocalDateTime.now())
                    .roles(roles)
                    .build();
            userService.saveUser(user);

            Optional<UserEntity> savedUsed = userService.findUserByEmail(request.getEmail());
            if (savedUsed.isEmpty()) {
                LOGGER.error("User {} cannot be created", request.getEmail());
                return new RegisterResponse(false, ResponseCode.Codes.AUTHENTICATION_ERROR.getCode(), ResponseCode.Codes.AUTHENTICATION_ERROR.name());
            }

            return new RegisterResponse(true, ResponseCode.Codes.SUCCESS.getCode(), ResponseCode.Codes.SUCCESS.name(),
                    savedUsed.get().getUserId(), savedUsed.get().getEmail(), savedUsed.get().getRoles());
        } catch (Exception e) {
            return new RegisterResponse(true, ResponseCode.Codes.EXCEPTION.getCode(), ResponseCode.Codes.EXCEPTION.name());
        }
    }

    public UserDetailsResponse fetchUserDetails() {
        try {
            UserPrincipal loggedInUser = WebUtil.getUserFromSession();
            Optional<UserEntity> userDetails = userService.findUserById(loggedInUser.getUserId());
            return userDetails.map(userEntity -> new UserDetailsResponse(
                    true, ResponseCode.Codes.SUCCESS.getCode(),
                    ResponseCode.Codes.SUCCESS.name(),
                    userEntity.getEmail(),
                    userEntity.getTimeCreated()
            )).orElseGet(() -> new UserDetailsResponse(false, ResponseCode.Codes.NOT_FOUND.getCode(), ResponseCode.Codes.NOT_FOUND.name()));
        } catch (AuthenticationException e) {
            LOGGER.error(e.getMessage(), e);
            return new UserDetailsResponse(false, ResponseCode.Codes.AUTHENTICATION_ERROR.getCode(), ResponseCode.Codes.AUTHENTICATION_ERROR.name());
        }
    }

    @Override
    public BaseResponse changePassword(ChangePasswordRequest request) {
        try {
            UserPrincipal loggedInUser = WebUtil.getUserFromSession();

            if (request == null) {
                LOGGER.error("Missing request body");
                return new BaseResponse(false, ResponseCode.Codes.EMPTY_REQUEST_BODY.getCode(), ResponseCode.Codes.EMPTY_REQUEST_BODY.name());
            }

            if (StringUtils.isEmpty(request.getNewPassword()) || StringUtils.isEmpty(request.getOldPassword())) {
                LOGGER.error("Missing request params");
                return new BaseResponse(false, ResponseCode.Codes.MISSING_REQUEST_PARAMS.getCode(), ResponseCode.Codes.MISSING_REQUEST_PARAMS.name());
            }

            if (request.getNewPassword().equals(request.getOldPassword())) {
                LOGGER.error("Passwords shouldn't be the same");
                return new BaseResponse(false, ResponseCode.Codes.PASSWORD_CHANGE_ERROR.getCode(), ResponseCode.Codes.PASSWORD_CHANGE_ERROR.name());
            }

            if (!passwordEncoder.matches(request.getOldPassword(), loggedInUser.getPassword())) {
                LOGGER.error("Current password does not match for user {}", loggedInUser.getUserId());
                return new BaseResponse(false, ResponseCode.Codes.PASSWORD_CHANGE_ERROR.getCode(), ResponseCode.Codes.PASSWORD_CHANGE_ERROR.name());
            }

            Optional<UserEntity> userDetails = userService.findUserById(loggedInUser.getUserId());
            if (userDetails.isEmpty()) {
                LOGGER.error("Missing user {}", loggedInUser.getUserId());
                return new BaseResponse(false, ResponseCode.Codes.NOT_FOUND.getCode(), ResponseCode.Codes.NOT_FOUND.name());
            }

            userDetails.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
            userService.saveUser(userDetails.get());

            return new BaseResponse(true, ResponseCode.Codes.SUCCESS.getCode(), ResponseCode.Codes.SUCCESS.name());
        } catch (AuthenticationException e) {
            LOGGER.error(e.getMessage(), e);
            return new BaseResponse(false, ResponseCode.Codes.AUTHENTICATION_ERROR.getCode(), ResponseCode.Codes.AUTHENTICATION_ERROR.name());
        }
    }

    private BaseResponse authenticationRequestValidation(Object request) {
        if (request == null) {
            LOGGER.error("Missing request body");
            return new BaseResponse(false, ResponseCode.Codes.EMPTY_REQUEST_BODY.getCode(), ResponseCode.Codes.EMPTY_REQUEST_BODY.name());
        }

        String email = null;
        String password = null;
        if (request instanceof LoginRequest loginRequest) {
            email = loginRequest.getEmail();
            password = loginRequest.getPassword();
        } else if (request instanceof RegisterRequest registerRequest){
            email = registerRequest.getEmail();
            password = registerRequest.getPassword();
        }

        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            LOGGER.error("Missing request params, email {}, password {}", email, password);
            return new BaseResponse(false, ResponseCode.Codes.MISSING_REQUEST_PARAMS.getCode(), ResponseCode.Codes.MISSING_REQUEST_PARAMS.name());
        }

        return new BaseResponse(true, ResponseCode.Codes.SUCCESS.getCode(), ResponseCode.Codes.SUCCESS.name());
    }

}
