package org.divya.congestion.tax.calculator.configuation.filter;

import lombok.Data;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class Slf4jMDCFilterConfiguration {

  @Bean
  public FilterRegistrationBean servletRegistrationBean() {
    final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    Slf4jMDCFilter log4jMDCFilterFilter = new Slf4jMDCFilter();
    registrationBean.setFilter(log4jMDCFilterFilter);
    registrationBean.setOrder(2);
    return registrationBean;
  }
}

