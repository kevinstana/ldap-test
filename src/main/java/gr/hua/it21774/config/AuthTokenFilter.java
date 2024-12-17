package gr.hua.it21774.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import gr.hua.it21774.helpers.CustomUserDetails;
import gr.hua.it21774.service.UserDetailsImpl;
import gr.hua.it21774.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl appUserDetailsServiceImpl;

    @Autowired
    private JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if ((jwt != null) && jwtUtils.validateToken(jwt)) {
                Claims claims = jwtUtils.getTokenPayload(jwt);
                String username = claims.getSubject();

                UserDetailsImpl userDetails = appUserDetailsServiceImpl.loadUserByUsername(username);
                CustomUserDetails customDetails = new CustomUserDetails(userDetails.getUsername(), "", new ArrayList<>(),
                        userDetails.getEmail(), userDetails.getTitle(), userDetails.getFirstName(),
                        userDetails.getLastName());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        customDetails, null, customDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}