package com.sportygroup.aviation.config.connectors;

import com.sportygroup.aviation.config.ExtProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Map;

import static com.sportygroup.aviation.utils.Constants.CALLER_ID;
import static com.sportygroup.aviation.utils.Constants.LOG_ID;

@Component
@Slf4j
public class AviationExtRestConnector  extends BaseRestConnector {

    @Autowired
    private ExtProperties extProperties;

    protected HttpHeaders getHeaders(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.set(CALLER_ID, MDC.get(LOG_ID));
        httpHeaders.setBasicAuth(extProperties.getExtAuthUserName(), extProperties.getExtAuthPassword()); //Assuming you need authentication to call ext service
        if(!CollectionUtils.isEmpty(headers)) {
            headers.keySet().forEach(key -> httpHeaders.set(key, headers.get(key)));
        }
        return httpHeaders;
    }
}
