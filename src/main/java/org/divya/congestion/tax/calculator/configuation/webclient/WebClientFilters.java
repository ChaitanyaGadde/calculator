package org.divya.congestion.tax.calculator.configuation.webclient;

import static net.logstash.logback.argument.StructuredArguments.kv;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

@UtilityClass
@Slf4j
public class WebClientFilters {

  public static ExchangeFilterFunction logRequest() {
    return (clientRequest, next) -> {
      log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
      clientRequest.headers()
          .forEach((name, values) -> values.forEach(value -> logRequestHeaders(name, value)));
      return next.exchange(clientRequest);
    };
  }

  private static void logRequestHeaders(String name, String value) {
    if (StringUtils.isNotEmpty(name) && !name.equalsIgnoreCase("Authorization")) {
      log.info("{}", kv(name, value));
    }
  }

}
