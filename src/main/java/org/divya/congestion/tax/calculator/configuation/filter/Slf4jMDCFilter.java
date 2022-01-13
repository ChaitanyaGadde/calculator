package org.divya.congestion.tax.calculator.configuation.filter;


import java.util.Objects;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class Slf4jMDCFilter extends OncePerRequestFilter {

  private static final String REQUEST_HEADER_TRANSACTION_ID = "x-transaction-id";
  private static final String REQUEST_HEADER_CLIENT = "x-client";
  private static final String REQUEST_HEADER_INTERNAL_CLIENT = "x-internal-client";
  private static final String RESPONSE_HEADER_TRANSACTION_ID = "x-transaction-id";
  private static final String MDC_TOKEN_KEY = "xTransactionId";
  private static final String MDC_CLIENT_IP_KEY = "xClientIp";
  private static final String MDC_CLIENT_KEY = "xClient";
  private static final String MDC_INTERNAL_CLIENT_KEY = "xInternalClient";
  private static final String FORWARDED_FOR = "x-forwarded-for";

  @Override
  protected void doFilterInternal(final HttpServletRequest request,
      final HttpServletResponse response, final FilterChain chain)
      throws java.io.IOException, ServletException {
    try {
      String token = getOrGenerateTransactionId(request);

      MDC.put(MDC_CLIENT_KEY, getClient(request));
      MDC.put(MDC_INTERNAL_CLIENT_KEY, getInternalClient(request));
      MDC.put(MDC_CLIENT_IP_KEY, getClientIp(request));
      MDC.put(MDC_TOKEN_KEY, token);

      response.addHeader(RESPONSE_HEADER_TRANSACTION_ID, token);

      chain.doFilter(request, response);
    } finally {
      MDC.remove(MDC_TOKEN_KEY);
      MDC.remove(MDC_CLIENT_IP_KEY);
      MDC.remove(MDC_CLIENT_KEY);
      MDC.remove(MDC_INTERNAL_CLIENT_KEY);
    }
  }

  private String getOrGenerateTransactionId(final HttpServletRequest request) {
    if (StringUtils.isNotEmpty(request.getHeader(REQUEST_HEADER_TRANSACTION_ID))) {
      return request.getHeader(REQUEST_HEADER_TRANSACTION_ID);
    }
    return UUID.randomUUID().toString().toUpperCase().replace("-", "");
  }

  private String getClientIp(final HttpServletRequest request) {
    if (Objects.nonNull(request.getHeader(FORWARDED_FOR))) {
      return request.getHeader(FORWARDED_FOR).split(",")[0];
    }
    return request.getRemoteAddr();
  }

  private String getClient(final HttpServletRequest request) {
    if (StringUtils.isNotEmpty(request.getHeader(REQUEST_HEADER_CLIENT))) {
      return request.getHeader(REQUEST_HEADER_CLIENT);
    }
    return "";
  }

  private String getInternalClient(final HttpServletRequest request) {
    if (StringUtils.isNotEmpty(request.getHeader(REQUEST_HEADER_INTERNAL_CLIENT))) {
      return request.getHeader(REQUEST_HEADER_INTERNAL_CLIENT);
    }
    return "";
  }

  @Override
  protected boolean isAsyncDispatch(final HttpServletRequest request) {
    return false;
  }

  @Override
  protected boolean shouldNotFilterErrorDispatch() {
    return false;
  }
}