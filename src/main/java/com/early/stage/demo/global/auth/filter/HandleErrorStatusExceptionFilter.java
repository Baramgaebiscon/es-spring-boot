package com.early.stage.demo.global.auth.filter;

import com.early.stage.demo.global.error.ErrorStatusException;
import com.early.stage.demo.global.util.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.filter.GenericFilterBean;

public class HandleErrorStatusExceptionFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ErrorStatusException ex) {
            ResponseUtil.setResponseToErrorResponse((HttpServletResponse) servletResponse, ex.getErrorCase());
        }
    }
}
