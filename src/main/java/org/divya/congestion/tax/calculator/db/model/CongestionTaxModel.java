package org.divya.congestion.tax.calculator.db.model;

import java.io.Serializable;
import java.time.LocalTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Entity(name = "congestion_tax_table")
public class CongestionTaxModel implements Serializable {

  @Id
  @Column(name = "id")
  private Integer id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "city_id", referencedColumnName = "id")
  private CityModel cityId;

  @Column(name = "cost")
  private Integer cost;

  @Column(name = "start_time")
  private LocalTime startTime;

  @Column(name = "end_time")
  private LocalTime endTime;

}
