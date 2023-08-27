package com.project.schoolmanagement.security.jwt;

import com.project.schoolmanagement.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${backendapi.app.jwtExpirationMs}")
    private Long jwtExpirationMs;

    @Value("${backendapi.app.jwtSecret}")
    private String jwtSecret;

    public boolean validateJwtToken(String jwtToken){
        try {
            Jwts.parser().setSigningKey(jwtToken).parseClaimsJwt(jwtToken);
            return true;
        } catch (ExpiredJwtException e) {
            LOGGER.error("Jwt token is expired : {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Jwt token is unsupported : {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Jwt token is invalid : {}", e.getMessage());
        } catch (SignatureException e) {
            LOGGER.error("Jwt Signature is invalid : {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("Jwt is empty : {}", e.getMessage());
        }
        return false;
    }

    public String generateJwtToken(Authentication authentication)
    {
        //get info of logged-in user from context
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return generateTokenFromUsername(userDetails.getUsername());
    }

    /**
     *
     * @param username of the user
     * @return signed jwt with algorithm and its expiration date
     */
    public String generateTokenFromUsername(String username)
    {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     *
     * @param token jwt token signed with algorithm
     * @return username for user
     */
    public String getUsernameFromJwtToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
