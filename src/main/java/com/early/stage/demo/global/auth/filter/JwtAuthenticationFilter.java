package com.early.stage.demo.global.auth.filter;

import com.early.stage.demo.global.auth.CustomUserDetailsService;
import com.early.stage.demo.global.error.ErrorStatusException;
import com.early.stage.demo.global.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || JwtUtil.prefixNotMatched(authorization)) {
            filterChain.doFilter(request, response);
        } else {
            String token = JwtUtil.extractTokenWithoutPrefix(authorization);

            Claims claims = JwtUtil.validateToken(token);
            if (claims.getSubject().equals("access")) {
                Long userId = Long.valueOf((Integer) claims.get("uid"));
                Authentication authResult = authenticate(userId);
                onSuccessfulAuthentication(authResult, request, response);
            } else {
                filterChain.doFilter(request, response);
            }

        }
    }


    private Authentication authenticate(Long userId) throws ErrorStatusException {

        UserDetails userDetails = customUserDetailsService.loadUserByUserId(
            userId);
        return new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
    }

    private void onSuccessfulAuthentication(Authentication authResult, HttpServletRequest request,
        HttpServletResponse response) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authResult);
    }


}
