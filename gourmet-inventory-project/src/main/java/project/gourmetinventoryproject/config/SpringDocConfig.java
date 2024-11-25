package project.gourmetinventoryproject.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
               .info(new io.swagger.v3.oas.models.info.Info()
                       .title("Gourmet Inventory Project API")
                       .version("1.0.0")
                       .description("API para gerenciamento de estoque de ingredientes e pratos")
                       .contact(new io.swagger.v3.oas.models.info.Contact()
                               .email("gourmet_inventory@outlook.com")
                               .name("Equipe de Desenvolvimento"))).components(new Components()
                .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
    }
}
