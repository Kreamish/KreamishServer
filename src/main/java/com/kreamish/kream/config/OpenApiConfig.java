package com.kreamish.kream.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
    title = "REST API",
    version = "1.0",
    description = "KREAMISH REST API",
    contact = @Contact(
        name = "Kreamish",
        url = "https://github.com/Kreamish/KreamishServer"
    )),
    security = {
        @SecurityRequirement(name = "bearerToken")
    }
)
public class OpenApiConfig {

}
