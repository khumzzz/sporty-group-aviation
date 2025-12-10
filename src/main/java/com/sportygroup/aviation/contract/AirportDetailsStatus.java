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
public class AirportDetailsStatus implements Serializable {
    @JsonProperty("errorCode")
    private String errorCode;
    @JsonProperty("errorDescription")
    private String errorDescription;
}
