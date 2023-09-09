package com.kreamish.kream.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
    title = "REST API",
    version = "1.0",
    description = "KREAMISH REST API",
    contact = @Contact(
        name = "Kreamish",
        url = "https://github.com/Kreamish/KreamishServer"
    ))
)
@SecurityScheme(
    type= SecuritySchemeType.HTTP,
    scheme = "Basic",
    bearerFormat = "Basic",
    in = SecuritySchemeIn.HEADER,
    name = "basicAuth",
    description = "Username에는 memberId 값, Password에는 kreamish를 입력해주세요."
)
public class OpenApiConfig {
}
