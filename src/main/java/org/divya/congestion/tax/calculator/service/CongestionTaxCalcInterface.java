package org.divya.congestion.tax.calculator.service;

import org.divya.congestion.tax.calculator.transport.request.CongestionRequestDTO;
import org.divya.congestion.tax.calculator.transport.response.CongestionResponse;
import org.springframework.stereotype.Component;

@Component
public interface CongestionTaxCalcInterface {

  CongestionResponse executeCalculation(CongestionRequestDTO congestionRequestDTO);
}
