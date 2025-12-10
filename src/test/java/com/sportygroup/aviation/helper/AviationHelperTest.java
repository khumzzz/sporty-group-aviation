package com.sportygroup.aviation.helper;

import com.sportygroup.aviation.config.connectors.AviationExtRestConnector;
import com.sportygroup.aviation.exceptions.AirportDetailsRetryException;
import com.sportygroup.aviation.model.AirportDetailsApiRequest;
import com.sportygroup.aviation.model.AirportDetailsApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AviationHelperTest {
    public static final String ICAOCODE = "ICAOCODE";
    @Mock
    private AviationExtRestConnector restConnector;
    @InjectMocks
    private AviationHelper aviationHelper;

    @BeforeEach
    void setUp() {
//        when(restConnector.callPostService(anyString(), any(java.net.URI.class), anyMap(), any(Object.class), any(Class.class))).thenReturn(buildAirportDetailsApiResponse());
    }

    @Test
    void testRequestExternalAirportDetailsWithException() {

        AirportDetailsRetryException exception = assertThrows(AirportDetailsRetryException.class, () -> {
            aviationHelper.requestExternalAirportDetails(buildAirportDetailsApiRequest());
        });
        assertTrue(exception.getMessage().contains("Failed to get airport details for"));
    }

    private AirportDetailsApiRequest buildAirportDetailsApiRequest() {
        return AirportDetailsApiRequest.builder().icaoCode(ICAOCODE).build();
    }

    private ResponseEntity<AirportDetailsApiResponse> buildAirportDetailsApiResponse() {
        AirportDetailsApiResponse unitedKingdom = AirportDetailsApiResponse.builder()
                .icaoCode(ICAOCODE)
                .location("United Kingdom")
                .build();
        return new ResponseEntity<>(unitedKingdom, HttpStatus.OK);
    }
}
