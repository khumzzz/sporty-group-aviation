package com.sportygroup.aviation.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

import static com.sportygroup.aviation.utils.Constants.*;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
    @Autowired
    private ExtProperties extProperties;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        populateId(request);

        response.setHeader(X_REQ_ID, MDC.get(LOG_ID));
        MDC.put(APP_ID, extProperties.getAppName());

        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            try {
                filterChain.doFilter(request, response);
            } finally {
                MDC.remove(LOG_ID);
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private void populateId(HttpServletRequest request) {
        String callerId = request.getHeader(CALLER_ID);
        if (StringUtils.isBlank(callerId)) {
            MDC.put(LOG_ID, UUID.randomUUID().toString());
        } else {
            MDC.put(LOG_ID, callerId);
        }
    }
}
