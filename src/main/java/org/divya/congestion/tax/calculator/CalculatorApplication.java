package org.divya.congestion.tax.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "org.divya.*")
@EntityScan("org.divya.congestion.tax.calculator.db.model")
@EnableJpaRepositories("org.divya.congestion.tax.calculator.db.repository")
public class CalculatorApplication {

  public static void main(String[] args) {
    SpringApplication.run(CalculatorApplication.class, args);
  }

}
