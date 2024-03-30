package com.early.stage.demo.global.auth.filter;

import com.early.stage.demo.domain.member.service.MemberLoginService;
import com.early.stage.demo.global.error.ErrorCase;
import com.early.stage.demo.global.error.ErrorStatusException;
import com.early.stage.demo.global.util.JwtUtil;
import com.early.stage.demo.global.util.ResponseUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

public class RefreshJwtFilter extends OncePerRequestFilter {

    private MemberLoginService memberLoginService;

    private final AntPathRequestMatcher refreshRequestMatcher = new AntPathRequestMatcher("/refresh",
        "POST");

    public RefreshJwtFilter(MemberLoginService memberLoginService) {
        this.memberLoginService = memberLoginService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        if (refreshRequestMatcher.matches(request)) {
            String authorization = request.getHeader("Authorization");
            if (authorization == null || JwtUtil.prefixNotMatched(authorization)) {
                filterChain.doFilter(request, response);
            } else {
                String token = JwtUtil.extractTokenWithoutPrefix(authorization);
                try {
                    Claims claims = JwtUtil.validateToken(token);
                    if (claims.getSubject().equals("refresh")) {
                        Long userId = Long.valueOf((Integer) claims.get("uid"));
                        Date lat = new Date((Long) claims.get("lat"));

                        checkMostRecentGeneratedToken(userId, lat);

                        ResponseUtil.addCookieWithHttpOnly(response, "accessToken",
                            JwtUtil.generateAccessToken(userId, lat));
                    } else {
                        filterChain.doFilter(request, response);
                    }
                } catch (ErrorStatusException ex) {
                    ResponseUtil.setResponseToErrorResponse(response, ex.getErrorCase());
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }


    private void checkMostRecentGeneratedToken(Long userId, Date date) throws ErrorStatusException {
        Date lastLoginDate = memberLoginService.findMemberLoginDate(userId);
        if (!date.equals(lastLoginDate)) {
            throw new ErrorStatusException(ErrorCase._401_NOT_MOST_RECENT_GENERATED_TOKEN);
        }
    }
}
