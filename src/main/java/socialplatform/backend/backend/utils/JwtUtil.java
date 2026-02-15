package socialplatform.backend.backend.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // JWT密钥
    @Value("${jwt.secret:mySecretKeyForJWTTokenGeneration2024}")
    private String secret;
    // 令牌过期时间
    @Value("${jwt.expiration:86400000}")
    private Long expiration;
    // 生成签名密钥
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String generateToken(Integer userId, String username, Integer roleId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("roleId", roleId);
        claims.put("type", "access_token");
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Integer getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("userId", Integer.class);
    }

    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("username", String.class);
    }

    public Integer getRoleIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("roleId", Integer.class);
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public String refreshToken(String token) {
        Claims claims = parseToken(token);
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date(System.currentTimeMillis() + expiration));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}