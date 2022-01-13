package org.divya.congestion.tax.calculator.configuation;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class Documentation {


  public static final String X_API_ID = "x-api-id";
  //private final BuildProperties buildProperties;

  @Value("${spring.application.name}")
  private String applicationName;

  @Bean
  public OpenAPI customOpenAPI() {
    log.info("Documentation = ENABLED");
    UUID uuid = UUID.randomUUID();

    return new OpenAPI()
        .servers(
            List.of(getServer("url to any routed destination", "url for Dev through http"),
                getServer("http://localhost:8080/", "url for localhost through http")
            ))
        .info(new Info()
            .extensions(Map.of(X_API_ID, uuid, "API Audience", "Car congestion tax calculators"))
            .title(applicationName)
            .version("1.0.0")
            .description("This is the API for congestion tax calc.")
            .contact(new Contact().name("divya Gadde")
                .email("gaddechaitu2323@gmail.com")));
    //.license(new License().name("Apache 2.0")
    //                      .url("http://springdoc.org"))
    // .termsOfService("add when requires"));
  }

  private Server getServer(String url, String description) {
    Server server = new Server();
    server.setUrl(url);
    server.setDescription(description);
    return server;
  }

  @Bean
  public OperationCustomizer customizeOperation() {
    return (operation, handlerMethod) ->
        operation
            .responses(
                operation
                    .getResponses()
                    .addApiResponse("422", new ApiResponse().description("Validation failed")));
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE", "PATCH");
      }
    };
  }

  @Bean
  public ObjectMapper objectMapper() {
    SimpleFilterProvider filters = new SimpleFilterProvider();
    filters.setFailOnUnknownId(false);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setFilterProvider(filters);
    objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return objectMapper;
  }
}
