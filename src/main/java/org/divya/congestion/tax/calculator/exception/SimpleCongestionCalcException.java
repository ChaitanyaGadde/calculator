package org.divya.congestion.tax.calculator.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class SimpleCongestionCalcException extends ResponseStatusException {

  private final HttpStatus status;
  private final String reason;

  public SimpleCongestionCalcException(HttpStatus status, String reason, Throwable cause) {
    super(status, reason, cause);
    this.status = status;
    this.reason = reason;
  }

  public SimpleCongestionCalcException(HttpStatus status, String reason) {
    super(status, reason);
    this.status = status;
    this.reason = reason;
  }

}
