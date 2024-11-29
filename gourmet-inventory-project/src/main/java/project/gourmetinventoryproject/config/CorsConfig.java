package project.gourmetinventoryproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000/",
                                "http://localhost/",
                                "http://localhost/api",
                                "http://nginx/api",
                                "http://nginx/",
                                "http://54.85.12.204/api",
                                "http://54.85.12.204/",
                                "http://10.0.0.97/api",
                                "http://10.0.0.97/")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
