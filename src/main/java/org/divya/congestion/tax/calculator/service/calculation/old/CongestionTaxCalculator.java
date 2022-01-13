package org.divya.congestion.tax.calculator.service.calculation.old;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.divya.congestion.tax.calculator.transport.old.Vehicle;

public class CongestionTaxCalculator {

  private static Map<String, Integer> tollFreeVehicles = new HashMap<>();
  // static can be enum returned case validation
  //method names with capitals

  static {
    tollFreeVehicles.put("Motorcycle", 0);
    tollFreeVehicles.put("Tractor", 1); //not in the requirements it was busses in requirements
    tollFreeVehicles.put("Emergency", 2);
    tollFreeVehicles.put("Diplomat", 3);
    tollFreeVehicles.put("Foreign", 4);
    tollFreeVehicles.put("Military", 5);

  }

  public int getTax(Vehicle vehicle, Date[] dates) {
    // assumption of dates in the linear order ad array i am not sure if it is even cooked or order,
    // what if an even is missed in between and comes in later from the reader
    //max per year will also be 60 kr
    Date intervalStart = dates[0]; // can be a wrong assumption itself && not reassigned
    int totalFee = 0;

    for (int i = 0; i < dates.length; i++) {
      Date date = dates[i];
      int nextFee = getTollFee(date, vehicle);
      int tempFee = getTollFee(intervalStart, vehicle);

      long diffInMillies = date.getTime() - intervalStart.getTime();
      long minutes = diffInMillies / 1000 / 60;

      if (minutes <= 60) {
        if (totalFee > 0) {
          totalFee -= tempFee;
        }
        if (nextFee >= tempFee) {
          tempFee = nextFee;
        }
        totalFee += tempFee;
      } else {
        totalFee += nextFee;
      }
    }

    if (totalFee > 60) {
      totalFee = 60;
    }
    return totalFee;
  }

  private boolean isTollFreeVehicle(Vehicle vehicle) {
    if (vehicle == null) { // null ! =
      return false;
    }
    String vehicleType = vehicle.getVehicleType(); // case returned enum
    return tollFreeVehicles.containsKey(vehicleType);
  }

  public int getTollFee(Date date, Vehicle vehicle) {
    if (isTollFreeDate(date) || isTollFreeVehicle(vehicle)) {
      return 0;
    }

    int hour = date.getHours();
    int minute = date.getMinutes();

    if (hour == 6 && minute >= 0 && minute <= 29) { //simplify case validation with case and db
      return 8;
    } else if (hour == 6 && minute >= 30 && minute <= 59) {
      return 13;
    } else if (hour == 7 && minute >= 0 && minute <= 59) {
      return 18;
    } else if (hour == 8 && minute >= 0 && minute <= 29) {
      return 13;
    } else if (hour >= 8 && hour <= 14 && minute >= 30 && minute <= 59) { // does not solve 0900 am !!
      return 8;
    } else if (hour == 15 && minute >= 0 && minute <= 29) {
      return 13;
    } else if (hour == 15 && minute >= 0 || hour == 16 && minute <= 59) {
      return 18;
    } else if (hour == 17 && minute >= 0 && minute <= 59) {
      return 13;
    } else if (hour == 18 && minute >= 0 && minute <= 29) {
      return 8;
    } else {
      return 0;
    }
  }

  private Boolean isTollFreeDate(Date date) {
    int year = date.getYear();
    int month = date.getMonth() + 1;
    int day = date.getDay() + 1;
    int dayOfMonth = date.getDate();

    if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
      return true;
    }

    if (year == 2013) {
      if ((month == 1 && dayOfMonth == 1) ||
          (month == 3 && (dayOfMonth == 28 || dayOfMonth == 29)) ||
          (month == 4 && (dayOfMonth == 1 || dayOfMonth == 30)) ||
          (month == 5 && (dayOfMonth == 1 || dayOfMonth == 8 || dayOfMonth == 9)) ||
          (month == 6 && (dayOfMonth == 5 || dayOfMonth == 6 || dayOfMonth == 21)) ||
          (month == 7) ||
          (month == 11 && dayOfMonth == 1) ||
          (month == 12 && (dayOfMonth == 24 || dayOfMonth == 25 || dayOfMonth == 26 || dayOfMonth == 31))) {
        return true;
      }
    }
    return false;
  }
}
