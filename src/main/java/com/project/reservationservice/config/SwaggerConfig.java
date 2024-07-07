package com.project.reservationservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("테이블링 예약 API")
                        .description("테이블링 예약 서비스를 위한 REST API 문서입니다.")
                        .contact(new Contact()
                        .name("이건우")
                                        .email("rjsdndl909@gmail.com")
                                        .url("https://github.com/geonuu09/Reservation-Service"))
                        .version("1.0"));
    }
}