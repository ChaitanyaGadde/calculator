package org.divya.congestion.tax.calculator.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import org.divya.congestion.tax.calculator.service.CongestionTaxCalcInterface;
import org.divya.congestion.tax.calculator.transport.request.CongestionRequestDTO;
import org.divya.congestion.tax.calculator.transport.response.CongestionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest
@ContextConfiguration(classes = CongestionTaxController.class)
class CongestionTaxControllerTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  CongestionTaxCalcInterface congestionTaxCalcInterface;

  @Test
  @DisplayName("Initial tax calculation based on the Daily method")
  void shouldCalculateTax_GivenRequest_whenPrivateVehicle() throws Exception {

    when(congestionTaxCalcInterface
        .executeCalculation(any(CongestionRequestDTO.class))).thenReturn(
        new CongestionResponse("AXB100K", new HashMap<>()));

    mockMvc.perform(post("/congestion/v1/calculate-tax")
        .header("headers", new HttpHeaders())
        .header("X-Transaction-ID", "dtiofjer-sdfd-dff")
        .header("X-Internal-Client", "anyinternalClient")
        .header("X-Client", "any external client")
        .content(getRequestJson(getCongestionRequestDTO()))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
  }

  private String getRequestJson(Object object) throws JsonProcessingException {
    return OBJECT_MAPPER.writeValueAsString(object);
  }

  private CongestionRequestDTO getCongestionRequestDTO() {
    return CongestionRequestDTO.builder().build();
  }
}