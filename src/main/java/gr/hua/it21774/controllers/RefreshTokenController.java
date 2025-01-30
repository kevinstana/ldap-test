package gr.hua.it21774.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.helpers.AuthDetails;
import gr.hua.it21774.requests.RefreshTokenRequest;
import gr.hua.it21774.responses.Tokens;
import gr.hua.it21774.responses.MessageRespone;
import gr.hua.it21774.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;

@RestController
public class RefreshTokenController {

    private final JwtService jwtUtils;

    public RefreshTokenController(JwtService jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        if (jwtUtils.validateRefreshToken(refreshToken)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Claims accessTokenClaims = (Claims) authentication.getDetails();

            String external = accessTokenClaims.get("external", String.class);

            boolean isExternal = (external != null) && (external.equals("true"));

            Long id = Long.valueOf(accessTokenClaims.getSubject());
            if (jwtUtils.isTokenPairValid(accessTokenClaims, refreshToken)) {
                AuthDetails authDetails = new AuthDetails(authentication, id, isExternal);
                Tokens tokens = jwtUtils.generateTokens(authDetails);

                return ResponseEntity.ok().body((tokens));

            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageRespone("Unauthorized"));
    }

}
