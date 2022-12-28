package com.template.authentication.config.constraints;

import com.template.authentication.config.UserPrincipal;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;

@Component
@Aspect
public class ActiveUserFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveUserFilter.class);

    @Around("@annotation(ActiveUser)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!"anonymousUser".equals(principal)) {
            UserPrincipal userPrincipal = (UserPrincipal) principal;
            if(!userPrincipal.isActive()) {
                LOGGER.error("User {} cannot access this resource", userPrincipal.getUserId());
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        }

        return pjp.proceed();
    }

}
