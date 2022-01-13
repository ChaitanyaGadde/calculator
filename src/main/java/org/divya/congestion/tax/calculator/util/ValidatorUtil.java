package org.divya.congestion.tax.calculator.util;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.divya.congestion.tax.calculator.transport.request.VehicleType;

@Slf4j
public class ValidatorUtil {

  private static final List<String> publicHolidays = List.of("2013-01-01",
      "2013-01-05",
      "2013-01-06",
      "2013-03-28",
      "2013-03-29",
      "2013-03-30",
      "2013-03-31",
      "2013-04-01",
      "2013-04-30",
      "2013-05-01",
      "2013-05-09",
      "2013-05-18",
      "2013-05-19",
      "2013-06-06",
      "2013-06-21",
      "2013-06-22",
      "2013-11-01",
      "2013-11-02",
      "2013-12-24",
      "2013-12-25",
      "2013-12-26",
      "2013-12-31");

  public static boolean isWeekend(LocalDateTime localDateTime) {
    switch (localDateTime.getDayOfWeek()) {
      case SATURDAY, SUNDAY -> {
        return Boolean.TRUE;
      }
      default -> {
        return Boolean.FALSE;
      }
    }
  }

  public static boolean isValidYear(LocalDateTime localDateTime) {
    if (localDateTime.getYear() == 2013) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  public static boolean isPublicHolidayOrDayBefore(LocalDateTime localDateTime) {
    LocalDate day = localDateTime.toLocalDate();
    LocalDate nextDay = day.plusDays(1);
    log.info("current date , next day {}", kv(day.toString(), nextDay.toString()));
    return publicHolidays.contains(nextDay.toString()) || publicHolidays.contains(day.toString());
  }

  public static boolean isNonTaxableVehicle(VehicleType vehicleType) {
    switch (vehicleType) {
      case BUSSES, DIPLOMAT_VEHICLES, EMERGENCY_VEHICLES, FOREIGN_VEHICLES, MILITARY_VEHICLES, MOTORCYCLES -> {
        return Boolean.TRUE;
      }
      default -> {
        return Boolean.FALSE;
      }
    }
  }
}
