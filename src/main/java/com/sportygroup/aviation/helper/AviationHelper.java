package com.sportygroup.aviation.helper;

import com.sportygroup.aviation.config.ExtProperties;
import com.sportygroup.aviation.config.connectors.AviationExtRestConnector;
import com.sportygroup.aviation.contract.AirportDetailsRequest;
import com.sportygroup.aviation.contract.AirportDetailsResponse;
import com.sportygroup.aviation.exceptions.AirportDetailsRetryException;
import com.sportygroup.aviation.model.AirportDetailsApiRequest;
import com.sportygroup.aviation.model.AirportDetailsApiResponse;
import com.sportygroup.aviation.utils.AviationApisUtil;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.sportygroup.aviation.utils.Constants.*;

@Component
@Slf4j
public class AviationHelper {


    @Autowired
    private ExtProperties extProperties;
    @Autowired
    private AviationExtRestConnector restConnector;

    public AirportDetailsResponse requestAirportDetails(AirportDetailsRequest request) {
        AirportDetailsResponse detailsResponse = AirportDetailsResponse.builder().build();
        AirportDetailsApiRequest extRequest = buildAirportDetailsApiRequest(request);
        AirportDetailsApiResponse externalResponse = requestExternalAirportDetails(extRequest);
        if(Objects.nonNull(externalResponse)) {
            BeanUtils.copyProperties(externalResponse, detailsResponse);
        } else {
            detailsResponse.setDetailsStatus(AviationApisUtil.errorCall(FAILED_TO_FETCH_AIRPORT_DETAILS));
        }
        return detailsResponse;
    }

    @Retry(name = "requestExternalAirportDetails", fallbackMethod = "requestExternalAirportDetailsFallback")
    public AirportDetailsApiResponse requestExternalAirportDetails(AirportDetailsApiRequest request) {
        try {
            URI uri = new URI(extProperties.getExtAirportDetailsEndPoint());
            Map<String, String> headers = new HashMap<>();
            headers.put(CALLER_ID, MDC.get(LOG_ID)); //add as many headers as required by ext end point
            ResponseEntity<AirportDetailsApiResponse> responseEntity = restConnector.callPostService(extProperties.getExtBaseUrl(), uri, headers, request, AirportDetailsApiResponse.class);
            return Objects.nonNull(responseEntity) ? responseEntity.getBody() : null;
        } catch (Exception e) {
            throw new AirportDetailsRetryException(FAILED_TO_GET_AIRPORT_DETAILS_FOR_ICAO_FOR +request.getIcaoCode());
        }
    }

    public AirportDetailsApiResponse requestExternalAirportDetails(AirportDetailsApiRequest request, RuntimeException e) {
        log.error(ERROR_ALERT, FAILED_TO_GET_AIRPORT_DETAILS_FOR_ICAO + request.getIcaoCode());
        return null;
    }

    private AirportDetailsApiRequest buildAirportDetailsApiRequest(AirportDetailsRequest request) {
        return AirportDetailsApiRequest.builder().icaoCode(request.getIcaoCode()).build();
    }
}
