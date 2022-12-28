package com.template.authentication.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
public class ResponseCode {

    public enum Codes {
        SUCCESS("1"),
        EXCEPTION("2"),
        DB_EXCEPTION("3"),
        EMPTY_REQUEST_BODY("4"),
        AUTHENTICATION_ERROR("5"),
        MISSING_REQUEST_PARAMS("6"),
        NOT_FOUND("7"),
        PASSWORD_CHANGE_ERROR("8");

        @Getter
        @Setter
        private String code;

        Codes(String code){
            this.code = code;
        }

        public static Codes findByKey(String key) {
            for (Codes c : values()) {
                if (c.name().equals(key)) {
                    return c;
                }
            }
            return null;
        }
    }

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String message;

    public ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
