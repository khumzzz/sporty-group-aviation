package com.sportygroup.aviation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiRoutesEnum {
    AIRPORT_DETAILS_REQUEST("airport-details-request-api", "airport-details-request api", "/service/airport-details", "direct:airport-details-request-proc", "airport-details-request-proc-id"),
    ;
    private final String id;
    private final String description;
    private final String url;
    private final String toRouteFrom;
    private final String toRouteId;
}
