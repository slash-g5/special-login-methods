package com.ss.auth.model.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SpecialHeaderAuthentication implements Authentication {

    private final List<GrantedAuthority> authorityList;
    private boolean isAuthenticated;
    private String password;

    private SpecialHeaderAuthentication(List<GrantedAuthority> authorityList, String password) {
        this.authorityList = authorityList;
        this.isAuthenticated = password == null;
        this.password = password;
    }

    public static SpecialHeaderAuthentication unauthenticated(String password){
        return new SpecialHeaderAuthentication(Collections.emptyList(), password);
    }

    public static SpecialHeaderAuthentication authenticated(){
        return new SpecialHeaderAuthentication(
                AuthorityUtils.createAuthorityList("ROLE_header"),
                null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return getName();
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException();
    }

    @Override
    public String getName() {
        return "Special Header";
    }

    public final String getPassword(){
        return password;
    }
}
