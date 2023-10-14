package com.ss.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.Optional;

@RestController
public class SecurityController {
    @GetMapping("/spring-sec/private")
    public String performOauthSpringSecurity(Authentication authentication){
        return "Hello " +
                Optional.of(authentication.getPrincipal())
                        .filter(OidcUser.class::isInstance)
                        .map(OidcUser.class::cast)
                        .map(OidcUser::getEmail)
                        .orElse(authentication.getName());
    }
}
