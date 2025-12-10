package com.sportygroup.aviation.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class AviationSecurityConf {

    public static final String[] WHITE_LIST = {"/swagger-ui/**", "/v3/api-docs/**", "/swagger.html"};

    @Autowired
    private AviationEntryPoint aviationEntryPoint;
    @Autowired
    private ExtProperties extProperties;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        final UserDetails user = User.builder().username(extProperties.getBasicAuthUserName())
                .password(passwordEncoder.encode(extProperties.getBasicAuthPassword())).authorities("ROLE_USER").build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        final AntPathRequestMatcher[] whiteList = Arrays.stream(WHITE_LIST)
                .map(AntPathRequestMatcher::antMatcher)
                .toArray(AntPathRequestMatcher[]::new);
        //white listing
        httpSecurity.authorizeHttpRequests(matcherRegistry ->
                matcherRegistry.requestMatchers(whiteList)
                        .permitAll().anyRequest().authenticated());

        //use basic auth
        httpSecurity.httpBasic(httpSecurityHttpBasicConfigurer ->
                httpSecurityHttpBasicConfigurer.authenticationEntryPoint(aviationEntryPoint));

        //disable CSRF
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

}
