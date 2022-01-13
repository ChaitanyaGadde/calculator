package org.divya.congestion.tax.calculator.service.calculation;

import static org.divya.congestion.tax.calculator.util.ValidatorUtil.isNonTaxableVehicle;
import static org.divya.congestion.tax.calculator.util.ValidatorUtil.isPublicHolidayOrDayBefore;
import static org.divya.congestion.tax.calculator.util.ValidatorUtil.isValidYear;
import static org.divya.congestion.tax.calculator.util.ValidatorUtil.isWeekend;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.divya.congestion.tax.calculator.db.model.CityModel;
import org.divya.congestion.tax.calculator.db.model.CongestionTaxModel;
import org.divya.congestion.tax.calculator.db.model.VehicleMovementModel;
import org.divya.congestion.tax.calculator.db.repository.CityRepository;
import org.divya.congestion.tax.calculator.exception.SimpleCongestionCalcException;
import org.divya.congestion.tax.calculator.service.CongestionTaxCalcInterface;
import org.divya.congestion.tax.calculator.transport.request.CongestionRequestDTO;
import org.divya.congestion.tax.calculator.transport.request.ExecutionType;
import org.divya.congestion.tax.calculator.transport.response.CongestionResponse;
import org.divya.congestion.tax.calculator.transport.response.CongestionValues;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CongestionTaxCalService implements CongestionTaxCalcInterface {

  private final CityRepository cityRepository;

  public CongestionTaxCalService(
      CityRepository cityRepository) {
    this.cityRepository = cityRepository;
  }

  @Override
  @Cacheable(
      value = "tax-calc-execution",
      key = "#congestionRequestDTO.registrationNumber")
  public CongestionResponse executeCalculation(CongestionRequestDTO congestionRequestDTO) {

    if (congestionRequestDTO.getExecutionType() == ExecutionType.DAILY) {
      return executePerDayTax(congestionRequestDTO);
    }
    throw new SimpleCongestionCalcException(HttpStatus.BAD_REQUEST, "Case Of calculation not matched");
  }

  private CongestionResponse executePerDayTax(CongestionRequestDTO congestionRequestDTO) {

    String cityCode = congestionRequestDTO.getCityCode();

    CityModel cityModel = cityRepository.findCityModelByCityCode(cityCode)
        .orElseThrow(() -> new SimpleCongestionCalcException(HttpStatus.BAD_REQUEST, "Check RequestCityCode"));

    List<CongestionTaxModel> congestionTaxModels = cityModel.getCongestionTaxModels();
    List<VehicleMovementModel> registeredTrips = cityModel.getVehicleMovementModels();

    List<CongestionValues> congestionValues = registeredTrips
        .stream()
        .filter(Objects::nonNull)
        .filter(daysFilterPredicate())
        .filter(vehicleMovementModel -> !isNonTaxableVehicle(vehicleMovementModel.getVehicleType()))
        .map(vehicleMovementModel -> getCongestionValues(congestionTaxModels, vehicleMovementModel))
        .sorted(Comparator.comparing(CongestionValues::getDate))
        .collect(Collectors.toList());

    List<CongestionValues> changedCongestionValues = sortPerTimeRange60MinsAndChooseMax(
        congestionValues);

    return new CongestionResponse(congestionRequestDTO.getRegistrationNumber(),
        getLocalDateToTaxAmountMap(changedCongestionValues));
  }

  private Map<LocalDate, Integer> getLocalDateToTaxAmountMap(List<CongestionValues> changedCongestionValues) {
    Map<LocalDate, Integer> collect = changedCongestionValues
        .stream()
        .collect(Collectors.groupingBy(congestionValues1 -> congestionValues1.getDate().toLocalDate(),
            Collectors.summingInt(CongestionValues::getAmount)));

    collect.entrySet().stream().filter(localDateIntegerEntry -> localDateIntegerEntry.getValue() > 60)
        .forEach(localDateIntegerEntry -> localDateIntegerEntry.setValue(60));
    return collect;
  }

  private List<CongestionValues> sortPerTimeRange60MinsAndChooseMax(List<CongestionValues> congestionValues) {
    List<CongestionValues> changedCongestionValues = new ArrayList<>();

    for (CongestionValues congestionValue : congestionValues) {

      LocalDateTime date = congestionValue.getDate();
      LocalDateTime timeInNext60Mins = date.plusMinutes(60);
      Optional<CongestionValues> max = congestionValues.stream().filter(
              congestionValues1 -> congestionValues1.getDate().equals(date)
                  || congestionValues1.getDate().isAfter(date) && congestionValues1.getDate()
                  .isBefore(timeInNext60Mins))
          .max(Comparator.comparing(CongestionValues::getAmount));

      changedCongestionValues.add(new CongestionValues(date, max.isPresent() ? max.get().getAmount() : 0));

    }
    return changedCongestionValues;
  }

  private CongestionValues getCongestionValues(List<CongestionTaxModel> congestionTaxModels,
      VehicleMovementModel vehicleMovementModel) {
    LocalDateTime timeRecorded = vehicleMovementModel.getTimeRecorded();
    LocalTime movementTime = timeRecorded.toLocalTime();

    Predicate<CongestionTaxModel> congestionTaxModelPredicate = congestionTaxModel ->
        congestionTaxModel.getStartTime().equals(movementTime) || movementTime.isAfter(
            congestionTaxModel.getStartTime()) && congestionTaxModel.getEndTime().equals(movementTime)
            || movementTime.isBefore(
            congestionTaxModel.getEndTime());

    Optional<CongestionTaxModel> any = congestionTaxModels.stream()
        .filter(Objects::nonNull)
        .filter(congestionTaxModelPredicate)
        .findAny();

    Integer congestionTax = 0;
    if (any.isPresent()) {
      congestionTax = any.get().getCost();
    }
    return new CongestionValues(timeRecorded, congestionTax);
  }

  private Predicate<VehicleMovementModel> daysFilterPredicate() {
    return vehicleMovementModel -> !isWeekend(vehicleMovementModel.getTimeRecorded())
        && isValidYear(vehicleMovementModel.getTimeRecorded())
        &&
        !isPublicHolidayOrDayBefore(
            vehicleMovementModel.getTimeRecorded());
  }
}
