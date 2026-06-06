package com.suman.notedown.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    private static final String SECURITY_SCHEME_NAME = "Bearer Authentication";
    private static final String SCHEME = "bearer";

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(
                        new Components().addSecuritySchemes(
                                SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme(SCHEME)
                                        .bearerFormat("JWT") // just for human understanding: can pass any string here
                        )
                );
    }

    @Bean
    public OpenApiCustomizer publicEndpointCustomizer() {
        return openApi -> {
            openApi.getPaths().forEach((path, pathItem) -> {
                if (path.startsWith("/auth/")) {
                    pathItem.readOperations()
                            .forEach(operation -> operation.setSecurity(List.of()));
                }
            });
        };
    }
}
