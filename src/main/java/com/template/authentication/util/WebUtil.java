package com.template.authentication.util;

import com.template.authentication.config.UserPrincipal;
import com.template.authentication.exception.AuthenticationException;
import com.template.authentication.dto.response.ResponseMessage;
import org.springframework.security.core.context.SecurityContextHolder;

public class WebUtil {

    public static UserPrincipal getUserFromSession() throws AuthenticationException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ("anonymousUser".equals(principal)) {
            throw new AuthenticationException(ResponseMessage.AUTHENTICATION_ERROR);
        }

        return (UserPrincipal) principal;
    }

}
