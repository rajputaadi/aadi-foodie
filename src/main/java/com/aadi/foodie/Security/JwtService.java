package com.aadi.foodie.Security;


import com.aadi.foodie.dto.UserDto;
import com.aadi.foodie.exception.ResourceNotFoundException;
import com.aadi.foodie.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
// this class is to perform operation with jwt
//create etc

@Service
public class JwtService {
    private final String REFRESH_TOKEN_TYPE = "refresh_token";
    private final String ACCESS_TOKEN_TYPE = "access_token ";
    private static final long EXPIRATION_TIME = 15 * 60 * 1000; //expiration time for access token
    private static final long EXPIRATION_TIME_REFRESH_TOKEN = 24 * 60 * 60 * 1000; //expiration time for access token
    private static final String SECRET = "eyqwqtbksfgleqwtwyrgqhsfvajsgfwqgkuygqwrtqwjedjsavb";

    private final UserService userService;

    public JwtService(UserService userService) {
        this.userService = userService;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // generate token now
    public String generateToken(String email, boolean isAccessToken) {
        long expTime = isAccessToken ? EXPIRATION_TIME : EXPIRATION_TIME_REFRESH_TOKEN;
        String tokenType = isAccessToken ? ACCESS_TOKEN_TYPE : REFRESH_TOKEN_TYPE;
        UserDto userDto = userService.getUsersByEmail(email);
        if (userDto == null) {
            throw new ResourceNotFoundException("User not found");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDto.getId());
        claims.put("email", userDto.getEmail());
        claims.put("type", tokenType);

        String token = Jwts.builder()
                .setSubject(userDto.getId())
                .claims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        return token;

    }

    // get user id from token
    public String getUserIdFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    // get email from token
    public String getEmailFromToken(String token) {
        return parseClaims(token).get("email", String.class);
    }

    // validate token
    public boolean validateToken(String token) {
        if (this.isTokenExpired(token)) {
            return false;
        }
        try {
            parseClaims(token);
            return true;
        } catch (JwtException e) {
            e.printStackTrace();
            return false;
        }
    }

    // check if token is expired
    public boolean isTokenExpired(String token) {
        try {
            parseClaims(token);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public boolean isRefreshToken(String token) {
        Claims claims = parseClaims(token);
        return claims.get("type").equals(REFRESH_TOKEN_TYPE);
    }

    public boolean isAccessToken(String token) {
        Claims claims = parseClaims(token);
        return claims.get("type").equals(ACCESS_TOKEN_TYPE);
    }
}
