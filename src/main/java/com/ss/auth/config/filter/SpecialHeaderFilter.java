package com.ss.auth.config.filter;

import com.ss.auth.model.security.authentication.SpecialHeaderAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class SpecialHeaderFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    public SpecialHeaderFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        if (!Collections.list(request.getHeaderNames()).contains("header-id")){
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("I see You want to login from special header");
        var pass = request.getHeader("header-id");
        var authRequest = SpecialHeaderAuthentication.unauthenticated(pass);
        try {
            var authManagerResponse = authenticationManager.authenticate(authRequest);
            var newContext = SecurityContextHolder.createEmptyContext();
            newContext.setAuthentication(authManagerResponse);
            SecurityContextHolder.setContext(newContext);
            filterChain.doFilter(request, response);
        }
        catch (AuthenticationException authenticationException){
            formForbiddenResponse(response, authenticationException);
        }
    }

    private static void formForbiddenResponse(HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-type", "text/plain;charset=utf-8");
        response.getWriter().println(authenticationException.getMessage());
    }

    private boolean isCorrect(String pass) {
        try{
            return pass.equals("special");
        }
        catch (Exception e){
            return false;
        }
    }
}
