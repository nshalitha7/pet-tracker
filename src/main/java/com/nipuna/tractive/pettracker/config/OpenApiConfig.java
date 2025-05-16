package com.nipuna.tractive.pettracker.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI petTrackerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pet Tracker API")
                        .description("REST API to manage pet tracking data and zone summary")
                        .version("v1.0"));
    }
}
