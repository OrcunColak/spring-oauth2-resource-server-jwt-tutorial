package com.colak.springoauth2resourceserverjwttutorial.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList("oauthToken"))
                .components(
                        new Components()
                                .addSecuritySchemes("oauthToken", createOAuthScheme())
                );
    }

    /**
     * This defines a security scheme of type "OAUTH2," using the "bearer" scheme, which is common for
     * JWT (JSON Web Token) authentication. It specifies the bearer format as "JWT."
     */
    private SecurityScheme createOAuthScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new OAuthFlows()
                        .authorizationCode(new OAuthFlow()
                                .tokenUrl("http://localhost:8080")
                                .scopes(new Scopes()
                                        .addString("role1", "role1 scope")
                                        .addString("role2", "role2 scope")
                                        .addString("GUEST", "GUEST scope")
                                )
                        ))
                ;
    }

    private Info apiInfo() {
        return new Info()
                .title("Authentication Service Api Doc")
                .version("1.0.0")
                .description("HTTP APIs to manage user registration and authentication.")
                .contact(new Contact().name("Orçun Çolak"));
    }
}
