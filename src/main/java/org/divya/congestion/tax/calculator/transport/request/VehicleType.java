package org.divya.congestion.tax.calculator.transport.request;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum VehicleType implements Serializable {

  EMERGENCY_VEHICLES("Emergency vehicles"),
  PRIVATE_VEHICLE("Private vehicle"),
  BUSSES("Busses"),
  DIPLOMAT_VEHICLES("Diplomat vehicles"),
  MOTORCYCLES("Motorcycles"),
  MILITARY_VEHICLES("Military vehicles"),
  FOREIGN_VEHICLES("Foreign vehicles");

  private String vehicleType;
}
