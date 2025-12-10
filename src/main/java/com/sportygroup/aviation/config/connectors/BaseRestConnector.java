package com.sportygroup.aviation.config.connectors;

import com.sportygroup.aviation.config.ExtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;
import java.util.Map;

import static com.sportygroup.aviation.utils.Constants.ERROR_ALERT;

@Component
@Slf4j
public abstract class BaseRestConnector {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ExtProperties extProperties;

    public <T> ResponseEntity<T> callPostService(String baseUrl, URI uri, Map<String, String> headers, Object request, Class<T> responseClass) {
        try {
            restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
            HttpEntity<Object> httpEntity = new HttpEntity<>(request, getHeaders(headers));
            return restTemplate.postForEntity(uri, httpEntity, responseClass);
        } catch (Exception e) {
            log.error(ERROR_ALERT, "API call failed "+ e.getMessage());
            throw e;
        }
    }

    protected abstract HttpHeaders getHeaders(Map<String, String> headers);
}