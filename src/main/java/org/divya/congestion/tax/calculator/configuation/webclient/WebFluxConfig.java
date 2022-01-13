package org.divya.congestion.tax.calculator.configuation.webclient;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

  @Override
  public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
    configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
  }
}
