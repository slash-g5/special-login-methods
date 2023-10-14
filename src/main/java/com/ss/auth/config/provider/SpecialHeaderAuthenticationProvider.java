package com.ss.auth.config.provider;

import com.ss.auth.model.security.authentication.SpecialHeaderAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

public class SpecialHeaderAuthenticationProvider implements AuthenticationProvider {

    private final List<String> passwords;

    public SpecialHeaderAuthenticationProvider(List<String> passwords) {
        this.passwords = passwords;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var specialHeaderAuthentication = (SpecialHeaderAuthentication) authentication;
        if (!passwords.contains(specialHeaderAuthentication.getPassword())){
            throw new BadCredentialsException("You are not special header");
        }
        return SpecialHeaderAuthentication.authenticated();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SpecialHeaderAuthentication.class.isAssignableFrom(authentication);
    }
}
