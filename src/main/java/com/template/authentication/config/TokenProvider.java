package com.template.authentication.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenProvider.class);

    private final AppProperties appProperties;

    @Autowired
    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String generateJwtToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date expiryDate = new Date(new Date().getTime() + appProperties.getAuth().getTokenExpirationMilliSec());
        boolean userIsActive = userPrincipal.isActive();

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getUserId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .claim("IsUserActive", userIsActive)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
    }

    public boolean isJwtTokenValid(final String authToken) {
        try {
            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return false;
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

}
