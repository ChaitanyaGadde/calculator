package org.divya.congestion.tax.calculator.configuation.webclient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.util.concurrent.TimeUnit;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.netty.http.client.HttpClient;

public class HttpClientUtils {

  private HttpClientUtils() {
  }

  public static ReactorClientHttpConnector fetchUpdatedConnector(Integer timeout) {

    HttpClient httpClient = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .doOnConnected(connection -> {
          connection.addHandlerLast(new ReadTimeoutHandler(timeout, TimeUnit.MILLISECONDS));
          connection.addHandlerLast(new WriteTimeoutHandler(timeout, TimeUnit.MILLISECONDS));
        });
    return new ReactorClientHttpConnector(httpClient);
  }
}
