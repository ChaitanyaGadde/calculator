package org.divya.congestion.tax.calculator.db.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "city")
public class CityModel implements Serializable {

  @Id
  @Column(name = "id")
  private Integer id;

  @Column(name = "city_name")
  private String cityName;

  @Column(name = "city_code")
  private String cityCode;

  @OneToMany(mappedBy = "cityId", cascade = CascadeType.ALL)
  private List<CongestionTaxModel> congestionTaxModels = new ArrayList<>();


  @OneToMany(mappedBy = "cityId", cascade = CascadeType.ALL)
  private List<VehicleMovementModel> vehicleMovementModels = new ArrayList<>();

}
