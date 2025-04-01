package com.rajmanda.config;

import com.rajmanda.entity.OauthToken;
import com.rajmanda.entity.User;
import com.rajmanda.repository.OauthTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
@Component
public class BearerTokenAuthFilter extends OncePerRequestFilter {

    @Autowired
    OauthTokenRepository tokenRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

//        if(authHeader != null && authHeader.startsWith("Bearer ") && !authHeader.substring(7).isBlank()) {
//            String accessToken = authHeader.substring(7);
//            User user = isTokenValid(accessToken);
//
//            if(user == null) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return;
//            }
//            else {
//                Authentication authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), null, user.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//
//        filterChain.doFilter(request, response);

        if(authHeader != null && authHeader.startsWith("Bearer ") && !authHeader.substring(7).isBlank()) {
            String bearerToken = authHeader.substring(7);
            Authentication bearerTokenAuthentication = new BearerTokenAuthenticaiton(bearerToken);

            try {
                Authentication authentication = authenticationManager.authenticate(bearerTokenAuthentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (AuthenticationException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private User isTokenValid(String token) {
        OauthToken oauthToken = tokenRepository.findById(token).orElse(null);

        if(oauthToken == null || oauthToken.getExpirationTime().isBefore(LocalDateTime.now())) {
            return null;
        }
        return oauthToken.getUser();
    }
}
