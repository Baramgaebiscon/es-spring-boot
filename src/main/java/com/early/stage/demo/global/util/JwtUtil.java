package com.early.stage.demo.global.util;

import com.early.stage.demo.global.error.ErrorCase;
import com.early.stage.demo.global.error.ErrorStatusException;
import com.early.stage.demo.global.property.JwtProperty;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    public static String generateAccessToken(Long userId, Date loginDate) throws ErrorStatusException {
        Date expiredDate = new Date(loginDate.getTime() + JwtProperty.accessValidTime);
        return generateToken(userId, expiredDate, loginDate, "access");
    }

    public static String generateRefreshToken(Long userId, Date loginDate) throws ErrorStatusException {
        Date expiredDate = new Date(loginDate.getTime() + JwtProperty.refreshValidTime);
        return generateToken(userId, expiredDate, loginDate, "refresh");
    }

    public static String generateToken(Long userId, Date expiredDate, Date loginDate, String tokenSub)
        throws ErrorStatusException {

        String generatedToken;
        try {
            generatedToken = Jwts.builder()
                .setHeader(Map.of("alg", JwtProperty.algorithm, "typ", "JWT"))
                .setExpiration(expiredDate)
                .setSubject(tokenSub)
                .addClaims(Map.of("uid", userId))
                .addClaims(Map.of("lat", loginDate))
                .signWith(JwtProperty.secretKey, JwtProperty.signatureAlgorithm)
                .compact();
        } catch (Exception ex) {
            throw new ErrorStatusException(ErrorCase._500_JWT_GENERATE_FAIL);
        }

        return generatedToken;
    }

    // TODO: make and throw appropriate exception
    /*
    public static Claims validateToken(String token) throws ErrorStatusException {

        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(JwtProperty.secretKey).build();
            return parser.parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException ex) {
            throw new ErrorStatusException();
        } catch (Exception ex) {
            throw new ErrorStatusException();
        }

    }
    */
}
