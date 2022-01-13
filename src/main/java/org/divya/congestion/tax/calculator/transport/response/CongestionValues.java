package org.divya.congestion.tax.calculator.transport.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CongestionValues {

  private LocalDateTime date;
  private Integer amount;
}
