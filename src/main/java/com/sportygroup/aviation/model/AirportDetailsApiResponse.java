package com.sportygroup.aviation.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sportygroup.aviation.contract.AirportDetailsStatus;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class AirportDetailsApiResponse implements Serializable {
    @JsonProperty("icaoCode")
    private String icaoCode;
    @JsonProperty("iataCode")
    private String iataCode;
    @JsonProperty("name")
    private String name;
    @JsonProperty("location")
    private String location;
    @JsonProperty("moreDetails")
    private String moreDetails;
}
