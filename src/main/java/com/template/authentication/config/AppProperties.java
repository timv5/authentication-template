package com.template.authentication.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Auth auth = new Auth();

    public static class Auth {

        private String tokenSecret;
        private long tokenExpirationMilliSec;
        private long expirationTime;

        public long getExpirationTime() {
            return expirationTime;
        }

        public void setExpirationTime(long expirationTime) {
            this.expirationTime = expirationTime;
        }

        public String getTokenSecret() {
            return tokenSecret;
        }

        public void setTokenSecret(String tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        public long getTokenExpirationMilliSec() {
            return tokenExpirationMilliSec;
        }

        public void setTokenExpirationMilliSec(long tokenExpirationMilliSec) {
            this.tokenExpirationMilliSec = tokenExpirationMilliSec;
        }

    }

    public Auth getAuth() {
        return auth;
    }
}
