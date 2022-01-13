package org.divya.congestion.tax.calculator.transport.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MovementRequestDTO(String regNumber, VehicleType vehicleType, LocalDateTime localDateTime) {

}
