package org.divya.congestion.tax.calculator.transport.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.divya.congestion.tax.calculator.transport.request.MovementRequestDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record VehicleMovementEvent(
    MovementRequestDTO movementRequestDTO,
    String registrationNumber) {

  //open end to create rabbit event
}
