package org.divya.congestion.tax.calculator.transport.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CongestionResponse(String registrationNumber, Map<LocalDate, Integer> perDayChargesResponses) {

}
