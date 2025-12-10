package com.sportygroup.aviation.contract;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class AirportDetailsResponse implements Serializable {
    @JsonProperty("detailsStatus")
    private AirportDetailsStatus detailsStatus;
    @JsonProperty("icaoCode")
    private String icaoCode;
    @JsonProperty("iataCode")
    private String iataCode;
    @JsonProperty("name")
    private String name;
    @JsonProperty("location")
    private String location;
}
