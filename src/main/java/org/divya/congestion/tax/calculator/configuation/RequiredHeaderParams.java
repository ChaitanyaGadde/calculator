package org.divya.congestion.tax.calculator.configuation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Parameters({
    @Parameter(in = ParameterIn.HEADER, description = "The calling client", name = "X-Client", required = true),
    @Parameter(in = ParameterIn.HEADER, description = "The transaction Id", name = "X-Transaction-ID", required = true),
    @Parameter(in = ParameterIn.HEADER, description = "The specify if internal client Id", name = "X-Internal-Client", required = true)
})
public @interface RequiredHeaderParams {

}
