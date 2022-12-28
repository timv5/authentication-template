package com.template.authentication.dto.response;

public class LoginResponse extends BaseResponse {

    private Long userId;
    private String type = "Bearer";
    private String accessToken;
    private Long expirationTime;

    public LoginResponse(boolean success, String code, String message) {
        super(success, code, message);
    }

    public LoginResponse(boolean success, String code, String message, Long userId, String accessToken, Long expirationTime) {
        super(success, code, message);
        this.userId = userId;
        this.accessToken = accessToken;
        this.expirationTime = expirationTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "userId=" + userId +
                ", type='" + type + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", expirationTime=" + expirationTime +
                '}';
    }
}
