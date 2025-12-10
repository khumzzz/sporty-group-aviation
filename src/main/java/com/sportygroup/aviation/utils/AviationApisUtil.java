package com.sportygroup.aviation.utils;

import com.sportygroup.aviation.contract.AirportDetailsStatus;

import static com.sportygroup.aviation.utils.Constants.*;

public class AviationApisUtil {

    public static AirportDetailsStatus successfulCall() {
        return callStatus(SUCCESS_CODE, SUCCESS_DESC);
    }

    public static AirportDetailsStatus errorCall(String description) {
        return callStatus(ERROR_CODE, description);
    }

    public static AirportDetailsStatus errorCall() {
        return callStatus(ERROR_CODE, ERROR_DESC);
    }

    public static AirportDetailsStatus callStatus(String errorCode, String errorDescription) {
        return AirportDetailsStatus.builder()
                .errorCode(errorCode)
                .errorDescription(errorDescription)
                .build();
    }
}
