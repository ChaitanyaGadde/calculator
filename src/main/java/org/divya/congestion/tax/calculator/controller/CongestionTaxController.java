package org.divya.congestion.tax.calculator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.divya.congestion.tax.calculator.exception.SimpleCongestionCalcException;
import org.divya.congestion.tax.calculator.configuation.RequiredHeaderParams;
import org.divya.congestion.tax.calculator.service.CongestionTaxCalcInterface;
import org.divya.congestion.tax.calculator.transport.request.CongestionRequestDTO;
import org.divya.congestion.tax.calculator.transport.response.CongestionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/congestion", produces = "application/json")
@Slf4j
public class CongestionTaxController {

  private final CongestionTaxCalcInterface congestionTaxCalcInterface;

  public CongestionTaxController(
      CongestionTaxCalcInterface congestionTaxCalcInterface) {
    this.congestionTaxCalcInterface = congestionTaxCalcInterface;
  }

  @PostMapping("/${app.api-endpoint-version.version-one}/calculate-tax")
  @Operation(summary = "scope tax calculation", description = "Used to execute tax calculation")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "OK",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = CongestionRequestDTO.class))),
      @ApiResponse(
          responseCode = "400",
          description = "Unable to execute",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = SimpleCongestionCalcException.class)))
  })
  @RequiredHeaderParams
  public ResponseEntity<CongestionResponse> calculateTax(
      @Valid @RequestBody CongestionRequestDTO congestionRequestDTO, @RequestHeader HttpHeaders headers) {
    log.info("Headers received {} : ", headers);
    return ResponseEntity.ok(congestionTaxCalcInterface.executeCalculation(congestionRequestDTO));
  }
}
