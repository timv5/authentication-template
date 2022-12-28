package com.template.authentication.service.dao;

import com.template.authentication.dto.request.ChangePasswordRequest;
import com.template.authentication.dto.request.LoginRequest;
import com.template.authentication.dto.request.RegisterRequest;
import com.template.authentication.dto.response.BaseResponse;
import com.template.authentication.dto.response.LoginResponse;
import com.template.authentication.dto.response.RegisterResponse;
import com.template.authentication.dto.response.UserDetailsResponse;

public interface AuthenticationService {

    public LoginResponse login(LoginRequest request);

    public RegisterResponse register(RegisterRequest request);

    public UserDetailsResponse fetchUserDetails();

    public BaseResponse changePassword(ChangePasswordRequest request);

}
