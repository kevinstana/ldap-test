package gr.hua.it21774.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import gr.hua.it21774.helpers.CustomUserDetails;
import io.jsonwebtoken.security.SignatureException;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

@Component
public class JwtUtils {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Map<String, String> buildCustomClaims(CustomUserDetails user) {
        Map<String, String> claims = new HashMap<>();
        claims.put("name", user.getFirstName() + " " + user.getLastName());
        claims.put("title", user.getTitle());
        claims.put("email", user.getEmail());
        return claims;
    }

    public String generateAccessToken(Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .subject((user.getUsername()))
                .claims(buildCustomClaims(user))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSignInKey())
                .compact();

    }

    public String generateRefreshToken(Authentication authentication, String accessToken) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        Claims claims = getTokenPayload(accessToken);
        return Jwts.builder()
                .subject((user.getUsername()))
                .claims(buildCustomClaims(user))
                .claim("aceessTokenIat", claims.getIssuedAt())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSignInKey())
                .compact();

    }

    public Claims getTokenPayload(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(this.getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims;
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
    }

    public String extractName(Claims claims) {
        return claims.get("name", String.class);
    }

    public String extractTitle(Claims claims) {
        return claims.get("title", String.class);
    }

    public String extractUsername(Claims claims) {
        return claims.get("usename", String.class);
    }

    public String extractEmail(Claims claims) {
        return claims.get("email", String.class);
    }

    public Date extractAccessTokenIat(Claims claims) {
        return claims.get("accessTokenIat", Date.class);
    }

    public Boolean validateToken(String token) {
        return !this.isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        Instant now = new Date().toInstant();
        Claims claims = getTokenPayload(token);
        return claims.getExpiration().before(Date.from(now));
    }

    private Boolean isTokenPairValid(String accessToken, String refreshToken) {
        Claims accessClaims = getTokenPayload(accessToken);
        Claims refreshClaims = getTokenPayload(refreshToken);
        Date accessTokenIat = extractAccessTokenIat(refreshClaims);

        return accessClaims.getIssuedAt() == (accessTokenIat);
    }
}