package org.divya.congestion.tax.calculator.configuation.webclient;


import static org.divya.congestion.tax.calculator.configuation.webclient.HttpClientUtils.fetchUpdatedConnector;
import static org.divya.congestion.tax.calculator.configuation.webclient.WebClientFilters.logRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Value("${app.env.webclient-timeout-millis}")
  private Integer timeout;

  @Bean
  @Qualifier(value = "webClient")
  public WebClient webClient() {
    return WebClient.builder()
        .clientConnector(fetchUpdatedConnector(timeout))
        .filter(logRequest())
        .build();
  }
}
