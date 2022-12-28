package com.template.authentication.dto.response;


import java.time.LocalDateTime;

public class UserDetailsResponse extends BaseResponse {

    private String email;
    private LocalDateTime timeCreated;

    public UserDetailsResponse(boolean success, String code, String message) {
        super(success, code, message);
    }

    public UserDetailsResponse(boolean success, String code, String message, String email, LocalDateTime timeCreated) {
        super(success, code, message);
        this.email = email;
        this.timeCreated = timeCreated;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Override
    public String toString() {
        return "UserDetailsResponse{" +
                "email='" + email + '\'' +
                ", timeCreated=" + timeCreated +
                '}';
    }
}
