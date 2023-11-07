package com.bbm.banking.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Official Documentation for the Online Banking Application(BBM Bank) API",
                description = "The Banking Api is a secure and feature-rich web-based platform that empowers users to " +
                        "manage their financial accounts conveniently and securely. Whether you are a bank customer or " +
                        "a fintech enthusiast, this application offers a comprehensive set of functionalities to help " +
                        "you take control of your financial life.",
                version = "1.0",
                contact = @Contact(
                        name = "Belmiro Mungoi",
                        email = "belmiromungoi@gmail.com",
                        url = "https://github.com/BelmiroMungoi"
                ),
                license = @License(
                        name = "Apache License 2.0",
                        url = "https://www.apache.org/licenses/"
                )
        ),
        servers = {
                @Server(
                        description = "Production Environment",
                        url = "http://ec2-13-49-245-175.eu-north-1.compute.amazonaws.com"
                ),
                @Server(
                        description = "Local Environment",
                        url = "http://localhost:8080"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "Bearer Authentication"
                )
        }
)
@SecurityScheme(
        name = "Bearer Authentication",
        description = "Authenticate in the API with your credentials, " +
                "get your JWT token and paste your JWT token auth description in the field down below",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
