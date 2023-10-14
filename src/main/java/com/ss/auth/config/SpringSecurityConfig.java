package com.ss.auth.config;

import com.ss.auth.config.provider.LordParetaAuthenticationProvider;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests( auth -> {
                            auth.requestMatchers("/spring-sec/**").authenticated();
                            auth.requestMatchers("/public").permitAll();
                        }
                )
                .formLogin(Customizer.withDefaults())
                .apply(new SpecialHeaderLoginConfigurer().addPassword("special-pass").addPassword("special-header"))
                .getHttpSecurity()
                .httpBasic(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults())
                .authenticationProvider(new LordParetaAuthenticationProvider())
                .build();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager(
                User.builder().username("shashank")
                        .password("{noop}password")
                        .authorities("ROLE_user")
                        .build()
        );
    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successLoginListener(){
        return event ->
                System.out.printf(
                        "Hurray Successful Auth [%s], %s \n",
                        event.getAuthentication().getClass().getName(),
                        event.getAuthentication().getName());
    }
}
