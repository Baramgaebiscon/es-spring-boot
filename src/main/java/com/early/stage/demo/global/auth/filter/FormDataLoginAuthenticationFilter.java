package com.early.stage.demo.global.auth.filter;

import com.early.stage.demo.domain.member.service.MemberLoginService;
import com.early.stage.demo.global.auth.CustomUserDetails;
import com.early.stage.demo.global.auth.LoginRequest;
import com.early.stage.demo.global.error.ErrorCase;
import com.early.stage.demo.global.error.ErrorStatusException;
import com.early.stage.demo.global.util.JwtUtil;
import com.early.stage.demo.global.util.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@AllArgsConstructor
public class FormDataLoginAuthenticationFilter extends OncePerRequestFilter {

    private final RequestMatcher requestMatcher = new AntPathRequestMatcher("/login",
        "POST");
    private AuthenticationManager authenticationManager;
    private MemberLoginService memberLoginService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        if (requestMatcher.matches(request)) {

            LoginRequest loginRequest = ResponseUtil.objectOf(request.getInputStream(), LoginRequest.class);
            String id = (loginRequest.getId() == null) ? "" : loginRequest.getId().strip();
            String password = (loginRequest.getPassword() == null) ? "" : loginRequest.getPassword().strip();

            Authentication authResult = attemptAuthentication(id, password);
            if (authResult == null) {
                throw new ErrorStatusException(ErrorCase._401_LOGIN_FAIL);
            }
            successfulAuthentication(response, authResult);

        } else {
            filterChain.doFilter(request, response);
        }
    }

    private Authentication attemptAuthentication(String id, String password) throws ErrorStatusException {
        if (id.isEmpty() || password.isEmpty()) {
            throw new ErrorStatusException(ErrorCase._400_BAD_FORM_DATA);
        }
        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(id,
            password);
        try {
            return authenticationManager.authenticate(authRequest);
        } catch (AuthenticationException ex) {
            throw new ErrorStatusException(ErrorCase._401_LOGIN_FAIL);
        }
    }

    private void successfulAuthentication(HttpServletResponse response, Authentication authResult)
        throws ErrorStatusException {
        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        long userId = userDetails.getMember().getMemberId();

        String accessToken = "";
        String refreshToken = "";
        Date loginDate = new Date();
        accessToken = JwtUtil.generateAccessToken(userId, loginDate);
        refreshToken = JwtUtil.generateRefreshToken(userId, loginDate);

        LocalDateTime localDateTime = LocalDateTime.ofInstant(loginDate.toInstant(), ZoneId.systemDefault());
        memberLoginService.modifyMemberLoginDate(userId, localDateTime);

        ResponseUtil.addCookieWithHttpOnly(response, "accessToken", accessToken);
        ResponseUtil.addCookieWithHttpOnly(response, "refreshToken", refreshToken);
    }

}
