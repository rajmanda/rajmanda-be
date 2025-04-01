package com.rajmanda.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    BearerTokenAuthFilter bearerTokenAuthFilter;

    private static final String[] WHITELIST_URLS = {
            "/h2-console/**",
            "/h2-console",
            "/register",
            "/login",
            "/grantcode",
            "/accountverification",
            "/generateresetpasswordlink",
            "/changepassword",
            "/refreshaccesstoken",
            "/",
            "/index.html",
            "/register.html",
            "/forgot_password.html",
            "/styles.css",
            "/sign-google.jpg",
            "/sing-git.jpg"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(WHITELIST_URLS).permitAll();
                    request.anyRequest().authenticated();
                })
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .addFilterAfter(bearerTokenAuthFilter, BasicAuthenticationFilter.class);

        return http.build();
    }
}