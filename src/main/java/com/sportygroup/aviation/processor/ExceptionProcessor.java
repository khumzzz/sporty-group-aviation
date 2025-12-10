package com.sportygroup.aviation.processor;

import com.sportygroup.aviation.utils.AviationApisUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class ExceptionProcessor implements Processor {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String EXCEPTION_FOUND = "Exception found ";

    @Override
    public void process(Exchange exchange) throws Exception {
        HashMap<String, Object> headerMap = new HashMap<>();
        headerMap.put(Exchange.HTTP_RESPONSE_CODE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        headerMap.put(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        exchange.getIn().setHeaders(headerMap);
        exchange.getIn().setBody(AviationApisUtil.errorCall());
        log.error(EXCEPTION_FOUND, (Exception)exchange.getProperty(Exchange.EXCEPTION_CAUGHT));
    }
}
