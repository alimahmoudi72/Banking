package com.example.banking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {
    @Bean
    public GroupedOpenApi util() {
        return GroupedOpenApi.builder()
                .group("dev")
                .packagesToScan("com")
                .pathsToMatch("/**")
                .build();
    }

    private OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info().title("Concurrent Banking System")
                        .description("")
                        .version("1.0")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")
                        )
                );
    }
}
