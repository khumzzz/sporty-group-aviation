package com.sportygroup.aviation.routes;

import com.sportygroup.aviation.contract.AirportDetailsRequest;
import com.sportygroup.aviation.contract.AirportDetailsResponse;
import com.sportygroup.aviation.enums.ApiRoutesEnum;
import com.sportygroup.aviation.processor.AirportDetailsProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.sportygroup.aviation.utils.Constants.SERVLET;

@Component
public class AirportRoutes  extends BaseRoute {

    public static final String AIRPORT_DETAILS_REQUEST = "Airport Details Request ${body}";
    public static final String AIRPORT_DETAILS_RESPONSE = "Airport Details Response ${body}";

    @Autowired
    private AirportDetailsProcessor airportDetailsProcessor;

    @Override
    public void configure() throws Exception {

        super.configure();
        restConfiguration()
                .component(SERVLET)
                .bindingMode(RestBindingMode.json);

        //END POINTS
        getPostDefinition(ApiRoutesEnum.AIRPORT_DETAILS_REQUEST, AirportDetailsRequest.class, AirportDetailsResponse.class);

        //DIRECT ROUTES
        from(ApiRoutesEnum.AIRPORT_DETAILS_REQUEST.getToRouteFrom())
                .routeId(ApiRoutesEnum.AIRPORT_DETAILS_REQUEST.getToRouteId())
                .log(LoggingLevel.INFO, this.getClass().getName(), AIRPORT_DETAILS_REQUEST)
                .process(airportDetailsProcessor)
                .log(LoggingLevel.INFO, this.getClass().getName(), AIRPORT_DETAILS_RESPONSE);
    }
}
