package com.sportygroup.aviation.processor;

import com.sportygroup.aviation.contract.AirportDetailsRequest;
import com.sportygroup.aviation.contract.AirportDetailsResponse;
import com.sportygroup.aviation.helper.AviationHelper;
import com.sportygroup.aviation.utils.AviationApisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.sportygroup.aviation.utils.Constants.*;

@Component
@Slf4j
public class AirportDetailsProcessor implements Processor {

    @Autowired
    private AviationHelper aviationHelper;

    @Override
    public void process(Exchange exchange) throws Exception {
        AirportDetailsRequest request = exchange.getIn().getBody(AirportDetailsRequest.class);
        AirportDetailsResponse response = AirportDetailsResponse.builder()
                .detailsStatus(AviationApisUtil.successfulCall()).build();
        try {
            if(Objects.nonNull(request)) {
                response = aviationHelper.requestAirportDetails(request);
            } else {
                response.setDetailsStatus(AviationApisUtil.errorCall(PLEASE_POPULATE_REQUEST));
            }
        } catch (Exception e) {
            log.error(FAILED_TO_FETCH_AIRPORT_DETAILS_FOR_ICAO_CODE, Objects.nonNull(request) ? request.getIcaoCode() : EMPTY_STRING);
            response.setDetailsStatus(AviationApisUtil.errorCall());
        }
        exchange.getIn().setBody(response);
    }
}
