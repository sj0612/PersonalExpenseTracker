package com.petracker.framework.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI personalExpenseTrackerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Personal Expense Tracker API")
                        .description("""
                                REST API for the Personal Expense Tracker application.
                                
                                **Authentication:** Use `/user/login` to obtain a JWT token, then click 
                                **Authorize** and enter `<your-token>` in the Bearer field.
                                """)
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("PeTracker Team")
                                .email("support@petracker.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Enter your JWT token obtained from /user/login")));
    }
}


