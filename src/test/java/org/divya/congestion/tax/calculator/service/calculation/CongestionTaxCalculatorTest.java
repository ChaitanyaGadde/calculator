package org.divya.congestion.tax.calculator.service.calculation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.divya.congestion.tax.calculator.service.calculation.old.CongestionTaxCalculator;
import org.divya.congestion.tax.calculator.transport.old.Car;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@Slf4j
class CongestionTaxCalculatorTest {

  static CongestionTaxCalculator congestionTaxCalculator;

  @BeforeAll
  static void init() {
    congestionTaxCalculator = new CongestionTaxCalculator();
  }

  private Date[] getDatesArray() throws ParseException {
    final SimpleDateFormat dateFormat
        = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    return new Date[]{
        dateFormat.parse("2013-01-14 21:00:00"),
        dateFormat.parse("2013-01-15 21:00:00"),
        dateFormat.parse("2013-02-07 06:23:27"),
        dateFormat.parse("2013-02-07 15:27:00"),
        dateFormat.parse("2013-02-08 06:27:00"),
        dateFormat.parse("2013-02-08 06:20:27"),
        dateFormat.parse("2013-02-08 14:35:00"),
        dateFormat.parse("2013-02-08 15:29:00"),
        dateFormat.parse("2013-02-08 15:47:00"),
        dateFormat.parse("2013-02-08 16:01:00"),
        dateFormat.parse("2013-02-08 16:48:00"),
        dateFormat.parse("2013-02-08 17:49:00"),
        dateFormat.parse("2013-02-08 18:29:00"),
        dateFormat.parse("2013-02-08 18:35:00"),
        dateFormat.parse("2013-03-28 14:07:27"),
        dateFormat.parse("2013-03-26 14:25:00"),
    };
  }

  @Test
  void should_getTax() throws ParseException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.parse("2013-01-14 21:00:00", formatter);

    localDateTime.getDayOfWeek();
    log.info("localDateTime.getDayOfWeek() {}", localDateTime.getDayOfWeek());
    LocalDate localDate = localDateTime.toLocalDate();
    String s = localDate.toString();
    log.info("ls {}", s);
    if (s.equalsIgnoreCase("2013-01-14")) {
      log.info("true");
    }
    String s1 = localDate.plusDays(-1).toString();
    log.info("ls {}", s1);
    int tax = congestionTaxCalculator.getTax(new Car(), getDatesArray());

    String formattedTime = localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    log.info("formattedTime {}", formattedTime);
  }

  @Test
  void should_getTollFee() {
  }
}