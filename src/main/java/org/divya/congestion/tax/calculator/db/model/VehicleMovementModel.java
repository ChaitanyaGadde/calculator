package org.divya.congestion.tax.calculator.db.model;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.divya.congestion.tax.calculator.transport.request.VehicleType;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "VEHICLE_MOVEMENT")
public class VehicleMovementModel implements Serializable {

  @Id
  @Column(name = "id")
  private Integer id;

  @Column(name = "reg_num")
  private String registrationNumber;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "city_id", referencedColumnName = "id")
  private CityModel cityId;

  @Enumerated(EnumType.STRING)
  @Column(name = "vehicle_type")
  private VehicleType vehicleType;

  @Column(name = "time_recorded")
  private LocalDateTime timeRecorded;

}
