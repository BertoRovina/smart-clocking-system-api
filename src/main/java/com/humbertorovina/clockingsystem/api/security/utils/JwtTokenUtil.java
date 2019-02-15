package com.humbertorovina.clockingsystem.api.security.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_ROLE = "role";
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Gets username (email) stored in the token JWT
     *
     * @param token
     * @return String
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * Returns expiration data from a JWT token
     *
     * @param token
     * @return Date
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /**
     * Creates a new token (refresh)
     *
     * @param token
     * @return String
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * Verifies and return if a JWT token is valid
     *
     * @param token
     * @return boolean
     */
    public boolean validToken(String token) {
        return !expiredToken(token);
    }

    /**
     * Returns a new JWT token based on the users data
     *
     * @param userDetails
     * @return String
     */
    public String retrieveToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        userDetails.getAuthorities().forEach(authority -> claims.put(CLAIM_KEY_ROLE, authority.getAuthority()));
        claims.put(CLAIM_KEY_CREATED, new Date());

        return generateToken(claims);
    }

    /**
     * Parse JWT Token to extract the information contained in its body
     *
     * @param token
     * @return Claims
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * Returns the expiration data based on the current data
     *
     * @return Date
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * Verifies if a JWT Token has expired
     *
     * @param token
     * @return boolean
     */
    private boolean expiredToken(String token) {
        Date expirationDate = this.getExpirationDateFromToken(token);
        if (expirationDate == null) {
            return false;
        }
        return expirationDate.before(new Date());
    }

    /**
     * Generates a new JWT Token tha contains the sourced data (claims)
     *
     * @param claims
     * @return String
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
}