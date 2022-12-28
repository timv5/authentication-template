package com.template.authentication.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            Optional<String> token = getJwt(request);
            if (token.isPresent() && StringUtils.isNotEmpty(token.get()) && !"undefined".equals(token.get())) {
                boolean isValid = tokenProvider.isJwtTokenValid(token.get());
                if (isValid) {
                    Long userId = tokenProvider.getUserIdFromToken(token.get());
                    UserDetails userDetails = userDetailsService.loadUserByUserId(userId);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> getJwt(final HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");

        if(StringUtils.isNotEmpty(authHeader) && authHeader.startsWith("Bearer")) {
            authHeader = authHeader.replace("Bearer", "");
            if ("null".equals(authHeader)) {
                return Optional.empty();
            } else {
                return Optional.of(authHeader);
            }
        }

        return Optional.empty();
    }
}
