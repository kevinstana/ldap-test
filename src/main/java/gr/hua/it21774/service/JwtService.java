package gr.hua.it21774.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import gr.hua.it21774.config.JwtProperties;
import gr.hua.it21774.helpers.AuthDetails;
import gr.hua.it21774.responses.AuthResponse;
import gr.hua.it21774.userdetails.AppUserDetails;
import io.jsonwebtoken.security.SignatureException;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.jwtSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public AuthResponse generateTokens(AuthDetails authDetails) {
        Authentication authentication = authDetails.getAuthentication();
        Long id = authDetails.getId();
        boolean isExternal = authDetails.getIsExternal();

        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();

        Instant instant = Instant.now();
        String accessToken = generateAccessToken(userDetails, instant, id, isExternal);
        String refreshToken = generateRefreshToken(userDetails, instant, accessToken, id);

        AuthResponse authResponse = new AuthResponse(accessToken, refreshToken);

        return authResponse;
    }

    private String generateAccessToken(AppUserDetails userDetails, Instant instant, Long id, boolean isExternal) {
        Map<String, String> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("role", userDetails.getRole().name());
        claims.put("email", userDetails.getEmail());

        if (isExternal) {
            claims.put("external", "true");
        }

        String accessToken = Jwts.builder()
                .subject(String.valueOf(id))
                .claims(claims)
                .issuedAt(Date.from(instant))
                .expiration(Date.from(instant.plus(jwtProperties.accessTokenDurationMs())))
                .signWith(getSignInKey())
                .compact();

        return accessToken;
    }

    private String generateRefreshToken(AppUserDetails userDetails, Instant instant, String accessToken, Long id) {
        Long accessTokenIat = getTokenPayload(accessToken).getIssuedAt().getTime();

        String refreshToken = Jwts.builder()
                .subject(String.valueOf(id))
                .claim("accessTokenIat", accessTokenIat)
                .issuedAt(Date.from(instant))
                .expiration(Date.from(instant.plus(jwtProperties.refreshTokenDurationMs())))
                .signWith(getSignInKey())
                .compact();

        return refreshToken;
    }

    public Claims getTokenPayload(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(this.getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims;
    }

    public Boolean validateAccessToken(String accessToken, String requestUri) {
        try {

            Claims claims = Jwts.parser().verifyWith(this.getSignInKey()).build()
                    .parseSignedClaims(accessToken)
                    .getPayload();

            // Check if a refresh token was used as an accessToken
            if (claims.containsKey("accessTokenIat")) {
                return false;
            }

            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            if (requestUri.equals("/api/refresh-token")) {
                Claims accessClaims = e.getClaims();

                // Check if a refresh token was used as an accessToken
                if (accessClaims.containsKey("accessTokenIat")) {
                    return false;
                }

                Instant accessTokenIat = Instant.ofEpochMilli(accessClaims.getIssuedAt().getTime());
                Instant now = Instant.now();

                Duration elapsed = Duration.between(accessTokenIat, now);
                boolean canRefresh = elapsed.compareTo(jwtProperties.refreshTokenDurationMs()) > 0;

                return canRefresh;
            }

            logger.error("JWT is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public Boolean validateRefreshToken(String token) {
        try {

            Jwts.parser().verifyWith(this.getSignInKey()).build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public Boolean isTokenPairValid(Claims accessClaims, String refreshToken) {
        Claims refreshClaims = getTokenPayload(refreshToken);

        String accessSub = accessClaims.getSubject();
        String refreshSub = refreshClaims.getSubject();

        Long accessIat = accessClaims.getIssuedAt().getTime();
        Long verifyAccessIat = refreshClaims.get("accessTokenIat", Long.class);

        boolean isTokenPairValid = Long.valueOf(accessIat).equals(verifyAccessIat)
                && (accessSub.equals(refreshSub));

        return isTokenPairValid;
    }
}