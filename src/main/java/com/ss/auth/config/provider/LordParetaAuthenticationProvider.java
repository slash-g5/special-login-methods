package com.ss.auth.config.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

public class LordParetaAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{
        var userName = authentication.getName();
        if (userName.equals("lord_pareta")){
            //Lord Pareta se Password Nahi Mangne Ka
            return UsernamePasswordAuthenticationToken.authenticated(
                    "lord_pareta",
                    null,
                    AuthorityUtils.createAuthorityList("ROLE_lord")
            );
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
