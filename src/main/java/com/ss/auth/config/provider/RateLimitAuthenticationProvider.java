package com.ss.auth.config.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimitAuthenticationProvider implements AuthenticationProvider {
    private final AuthenticationProvider delegate;
    private final ConcurrentHashMap<String, Instant> cache = new ConcurrentHashMap<>();

    public RateLimitAuthenticationProvider(AuthenticationProvider delegate) {
        this.delegate = delegate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var delegateAuthentication = delegate.authenticate(authentication);
        if(delegateAuthentication == null){
            return null;
        }
        if(updateCache(delegateAuthentication)){
            return delegateAuthentication;
        }
        throw new BadCredentialsException("Hey Hey Login Fast Like This is terrible,\nWe have low budget can't allow this");
    }

    private boolean updateCache(Authentication delegateAuthentication) {
        if(cache.get(delegateAuthentication.getName()) == null ||
                cache.get(delegateAuthentication.getName()).isBefore(Instant.now().minusSeconds(60))) {
            cache.put(delegateAuthentication.getName(), Instant.now());
            return true;
        }
        return false;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return delegate.supports(authentication);
    }
}
