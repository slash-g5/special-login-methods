package com.ss.auth.config;

import com.ss.auth.config.filter.SpecialHeaderFilter;
import com.ss.auth.config.provider.SpecialHeaderAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;


public class SpecialHeaderLoginConfigurer extends AbstractHttpConfigurer<SpecialHeaderLoginConfigurer, HttpSecurity> {
    private final List<String> passwords = new ArrayList<>();

    @Override
    public void init(HttpSecurity http) {
        http.authenticationProvider(new SpecialHeaderAuthenticationProvider(passwords));
    }

    @Override
    public void configure(HttpSecurity http) {
        var authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(new SpecialHeaderFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
    }

    public SpecialHeaderLoginConfigurer addPassword(String password){
        this.passwords.add(password);
        return this;
    }

    public HttpSecurity getHttpSecurity(){
        return super.getBuilder();
    }
}
