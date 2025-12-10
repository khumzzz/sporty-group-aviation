package com.sportygroup.aviation.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AviationEntryPoint  extends BasicAuthenticationEntryPoint {

    @Autowired
    private ExtProperties extProperties;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) {
        log.error("Unauthorized call");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    }

    @Override
    public void afterPropertiesSet(){
        setRealmName(extProperties.getAppName());
        super.afterPropertiesSet();
    }
}
