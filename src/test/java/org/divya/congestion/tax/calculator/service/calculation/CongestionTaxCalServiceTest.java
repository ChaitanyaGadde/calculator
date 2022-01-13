package org.divya.congestion.tax.calculator.service.calculation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.divya.congestion.tax.calculator.db.model.CityModel;
import org.divya.congestion.tax.calculator.db.model.CongestionTaxModel;
import org.divya.congestion.tax.calculator.db.model.VehicleMovementModel;
import org.divya.congestion.tax.calculator.db.repository.CityRepository;
import org.divya.congestion.tax.calculator.exception.SimpleCongestionCalcException;
import org.divya.congestion.tax.calculator.transport.request.CongestionRequestDTO;
import org.divya.congestion.tax.calculator.transport.request.ExecutionType;
import org.divya.congestion.tax.calculator.transport.request.VehicleType;
import org.divya.congestion.tax.calculator.transport.response.CongestionResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@Slf4j
class CongestionTaxCalServiceTest {

  public static final LocalDateTime TIME_RECORDED = LocalDateTime.of(2013, 01, 3, 06, 11);
  @Mock
  CityRepository cityRepository;

  @InjectMocks
  CongestionTaxCalService congestionTaxCalService;

  @BeforeEach
  void init() {
    congestionTaxCalService = new CongestionTaxCalService(cityRepository);
  }

  /*
  Can write parametrized tests as well for the sake of saving time and showing writing a simple test
   */
  @Test
  @DisplayName("Green Path scenario test for the given request")
  void should_ExecuteCongestionTaxCalc_GivenRequest() {

    CityModel cityModel = getCityModel();

    when(cityRepository.findCityModelByCityCode(anyString())).thenReturn(Optional.of(cityModel));

    CongestionResponse congestionResponse = congestionTaxCalService.executeCalculation(
        getCongestionRequestDTO(ExecutionType.DAILY));

    Assertions.assertNotNull(congestionResponse);

  }

  @Test
  @DisplayName("Test to Create Exception")
  void should_GivenExecuteCongestionTaxCalc_WhenExecutionCaseNotAvailable() {

    // when(cityRepository.findCityModelByCityCode(anyString())).thenReturn(Optional.of(cityModel));

    SimpleCongestionCalcException simpleCongestionCalcException = assertThrows(SimpleCongestionCalcException.class,
        () -> {
          CongestionResponse congestionResponse = congestionTaxCalService.executeCalculation(getCongestionRequestDTO(
              ExecutionType.MONTHLY));
        });

    Assertions.assertEquals("Case Of calculation not matched", simpleCongestionCalcException.getReason());

  }

  private CityModel getCityModel() {
    return CityModel.builder()
        .cityCode("GOT")
        .cityName("GOTHENBERG")
        .congestionTaxModels(getCongestionTaxModels())
        .vehicleMovementModels(getVehicleMovements())
        .id(1)
        .build();
  }

  private List<VehicleMovementModel> getVehicleMovements() {
    List<VehicleMovementModel> models = new ArrayList<>();
    models.add(getMovementModel(TIME_RECORDED));
    models.add(getMovementModel(TIME_RECORDED.plusDays(3)));
    models.add(getMovementModel(TIME_RECORDED.plusDays(20)));
    models.add(getMovementModel(TIME_RECORDED.plusDays(39)));
    models.add(getMovementModel(TIME_RECORDED.plusDays(3).plusHours(2)));
    return models;
  }

  private VehicleMovementModel getMovementModel(LocalDateTime timeRecorded) {
    return VehicleMovementModel.builder()
        .registrationNumber("AXB100K")
        .vehicleType(VehicleType.PRIVATE_VEHICLE)
        .timeRecorded(timeRecorded)
        .build();
  }

  private List<CongestionTaxModel> getCongestionTaxModels() {
    List<CongestionTaxModel> congestionTaxModels = new ArrayList<>();
    congestionTaxModels.add(getCongestionTaxModel(LocalTime.of(6, 29), LocalTime.of(6, 0), 10));
    congestionTaxModels.add(getCongestionTaxModel(LocalTime.of(8, 29), LocalTime.of(8, 0), 100));
    congestionTaxModels.add(getCongestionTaxModel(LocalTime.of(9, 29), LocalTime.of(9, 0), 12));
    congestionTaxModels.add(getCongestionTaxModel(LocalTime.of(17, 29), LocalTime.of(11, 0), 8));
    return congestionTaxModels;
  }

  private CongestionTaxModel getCongestionTaxModel(LocalTime ofendtime, LocalTime startTime, int cost) {
    return CongestionTaxModel.builder()
        .cost(cost)
        .endTime(ofendtime)
        .startTime(startTime)
        .build();
  }

  private CongestionRequestDTO getCongestionRequestDTO(ExecutionType daily) {
    return CongestionRequestDTO.builder()
        .cityCode("GOT")
        .executionType(daily)
        .registrationNumber("AXB100K")
        .vehicleType(VehicleType.PRIVATE_VEHICLE)
        .build();
  }
}