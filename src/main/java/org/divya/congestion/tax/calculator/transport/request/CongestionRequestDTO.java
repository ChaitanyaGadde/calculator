package org.divya.congestion.tax.calculator.transport.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = CongestionRequestDTO.Builder.class)
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class CongestionRequestDTO {

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {

  }

  @Valid
  @NotNull
  private String registrationNumber;
  private VehicleType vehicleType;
  private ExecutionType executionType;
  private String cityCode;
}
